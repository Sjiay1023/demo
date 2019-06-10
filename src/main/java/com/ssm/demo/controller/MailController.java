package com.ssm.demo.controller;

import com.ssm.demo.response.BaseResponse;
import com.ssm.demo.response.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author o_0sky
 * @date 2019/5/28 14:46
 */
@RestController
public class MailController {
    private static final Logger log = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    @RequestMapping(value = "mail/send",method = RequestMethod.GET)
    public BaseResponse sendMail(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            rabbitTemplate.setExchange(env.getProperty("mail.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("mail.routing.key.name"));
            rabbitTemplate.convertAndSend(MessageBuilder.withBody("mail发送".getBytes("UTF-8")).build());

        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("邮件发送完毕-----");
        return response;
    }
}
