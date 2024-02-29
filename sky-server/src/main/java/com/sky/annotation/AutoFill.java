package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: sky-take-out-backend
 * @description: 自定义注解，用于实现公共字段自动填充
 * @author: MichaelLong
 * @create: 2024-02-29 23:00
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {

    //数据库操作类型：UPDATE、INSERT
    OperationType value();

}
