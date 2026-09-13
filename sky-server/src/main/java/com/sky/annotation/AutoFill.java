package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    // 定义一个枚举类型属性，用于获取被自动填充方法的类型是insert还是update
    // 若为insert，要自动填充createTime，updateTime，createUser，updateUser
    // 若为update，要自动填充updateTime，updateUser
    OperationType value();
}
