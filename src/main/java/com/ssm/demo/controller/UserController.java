package com.ssm.demo.controller;

import com.ssm.demo.aop.AopMessage;
import com.ssm.demo.aop.UseCaseAnnotation;
import com.ssm.demo.domain.User;
import com.ssm.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 业务控制器
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @AopMessage(description="根据name查找")
    @RequestMapping(value = "find")
    public User findUserByName(){
        return userService.find("jay");
    }

    @RequestMapping(value = "like/{name}")
    public List<User> findUserLikeName(@PathVariable("name") String name){
        return userService.findUserLikeName(name);
    }

    @RequestMapping(value = "search/{id}")
    public User findUserById(@PathVariable("id") int id){
        return userService.findUserById(id);
    }

}
