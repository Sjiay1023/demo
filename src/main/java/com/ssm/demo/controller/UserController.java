package com.ssm.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.demo.aop.AopMessage;
import com.ssm.demo.domain.Student;
import com.ssm.demo.domain.User;
import com.ssm.demo.domain.UserLog;
import com.ssm.demo.response.BaseResponse;
import com.ssm.demo.response.StatusCode;
import com.ssm.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 业务控制器
 */
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    @AopMessage(description="根据name查找")
    @RequestMapping(value = "find")
    public Student findUserByName(){
        return userService.find("jay");
    }

    @RequestMapping(value = "like/{name}")
    public List<Student> findUserLikeName(@PathVariable("name") String name){
        return userService.findUserLikeName(name);
    }

    @RequestMapping(value = "search/{id}")
    public Student findUserById(@PathVariable("id") int id){
        return userService.findUserById(id);
    }

    @GetMapping("/user/{id}")
    public  Student getById(@PathVariable("id") Integer id){
        return userService.getById(id);
    }

    @GetMapping("/users")
    public List<Student> list(){
        return userService.list();
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public void insert(@RequestBody Student student){
        userService.insert(student);
    }

    @GetMapping("user/course/{id}")
    public Student selectCourseById(@PathVariable("id") Integer id){
        return userService.selectCourseById(id);
    }

    @RequestMapping(value = "user/login",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse login(@RequestParam("userName") String userName,@RequestParam("password") String password){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            User user = userService.selectByUserNamePassword(userName,password);
            if(user!=null){
                //TODO:异步写用户日志
                try {
                    UserLog userLog = new UserLog(userName,"login","login",objectMapper.writeValueAsString(user));
                    userLog.setCreateTime(new Date());
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(env.getProperty("log.user.exchange.name"));
                    rabbitTemplate.setRoutingKey(env.getProperty("log.user.routing.key.name"));

                    Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(userLog)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                    //发送消息写法二
                    message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
                    rabbitTemplate.convertAndSend(message);

                     /*UserLog log=new UserLog(userName,"Login","login",objectMapper.writeValueAsString(user));
                    userLogMapper.insertSelective(log);*/ //同步

                    /*MessageProperties properties=new MessageProperties();
                    properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
                    Message message=new Message(objectMapper.writeValueAsBytes(userLog),properties);*/ //发送消息写法一
                }catch (Exception e){
                    e.printStackTrace();
                }
                //TODO：塞权限数据-资源数据-视野数据
            }else{
                response = new BaseResponse(StatusCode.Fail);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

}
