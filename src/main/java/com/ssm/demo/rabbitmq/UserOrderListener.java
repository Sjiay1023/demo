package com.ssm.demo.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.ssm.demo.dao.UserOrderMapper;
import com.ssm.demo.service.ConcurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Administrator on 2018/8/30.
 */
@Component("userOrderListener")
public class UserOrderListener {

    private static final Logger log= LoggerFactory.getLogger
            (UserOrderListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private ConcurrencyService concurrencyService;

    @Autowired
    private Environment env;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "local.user.order.queue", autoDelete = "false"),
            exchange = @Exchange(value = "local.user.order.exchange", type = "topic"),
            key = "local.user.order.routing.key"))
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag=message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body=message.getBody();
            /*UserOrderDto entity=objectMapper.readValue(body,
UserOrderDto.class);
            log.info("用户商城抢单监听到消息： {} ",entity);

            UserOrder userOrder=new UserOrder();
            BeanUtils.copyProperties(entity,userOrder);
            userOrder.setStatus(1);
            userOrderMapper.insertSelective(userOrder);*/

            String mobile=new String(body,"UTF-8");
            log.info("监听到抢单手机号： {} ",mobile);

            concurrencyService.manageRobbing(String.valueOf(mobile));
            channel.basicAck(tag,false);
        }catch (Exception e){
            log.error("用户商城下单 发生异常：",e.fillInStackTrace());
            channel.basicReject(tag,false);
        }
    }
}
