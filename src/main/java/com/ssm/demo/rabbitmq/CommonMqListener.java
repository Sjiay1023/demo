package com.ssm.demo.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.demo.dao.UserLogMapper;
import com.ssm.demo.domain.UserLog;
import com.ssm.demo.service.MailService;
import com.ssm.demo.service.UserLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author o_0sky
 * @date 2019/5/21 19:01
 */
@Component
public class CommonMqListener {
    private static final Logger log= LoggerFactory.getLogger(CommonMqListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private MailService mailService;


    /**
     * 监听消费用户日志
     * @param message
     */
    @RabbitListener(queues = "${log.user.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeUserLogQueue(@Payload byte[] message){
        try {
            UserLog userLog=objectMapper.readValue(message, UserLog.class);
            log.info("监听消费用户日志 监听到消息： {} ",userLog);

            userLogService.insertSelective(userLog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @RabbitListener(queues = "${mail.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMailQueue(@Payload byte[] message){
        try{
            log.info("监听消费邮件发送 监听到消息： {} ",new String(message,"UTF-8"));
            mailService.sendEmail();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
