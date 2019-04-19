package com.ssm.demo.aop;

import java.lang.annotation.*;

/**
 * 统计方法调用次数注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseCaseAnnotation {
    int id() default 0;
}
