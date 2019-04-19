package com.ssm.demo.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录一次请求中，流经的所有方法的调用以及次数
 */
@Component
@Aspect
public class AopTest {
    private static final Logger logger = LoggerFactory.getLogger(AopTest.class);

    @Before("within(com.ssm.demo..*) && @annotation(aopMessage)")//前置通知
    public void beforeExecution(JoinPoint joinPoint,AopMessage aopMessage){
        //System.out.println("开始"+aopMessage.description());
        logger.info("执行 " + aopMessage.description() + " 开始");

    }

    @AfterReturning("within(com.ssm.demo..*) && @annotation(aopMessage)")//后置返回
    public void afterReturningLogger(JoinPoint joinPoint,AopMessage aopMessage){
        //System.out.println("结束"+aopMessage.description());
        logger.info("执行 " + aopMessage.description() + " 结束");
    }

}
