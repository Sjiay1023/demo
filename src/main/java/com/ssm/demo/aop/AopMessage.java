package com.ssm.demo.aop;

import java.lang.annotation.*;

/**
 * Aop注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopMessage {
    public String description();
}
