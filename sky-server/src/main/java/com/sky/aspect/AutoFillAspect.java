package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    // 定义切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void pt(){}

    @Before("pt() && @annotation(autoFill)")
    public void autoFillBefore(JoinPoint jp, AutoFill autoFill){
        log.info("自动填充字段...");
        // 获取自动填充方法类型是insert还是update
        OperationType operationType = autoFill.value();
        // 获取原始方法参数
        Object[] args = jp.getArgs();
        Object entity = args[0];
        // 填充字段
        try {
            // insert和update统一填充步骤
            entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class).invoke(entity, LocalDateTime.now());
            entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class).invoke(entity, BaseContext.getCurrentId());
            // insert特有的填充步骤
            if(operationType == OperationType.INSERT) {
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class).invoke(entity, BaseContext.getCurrentId());
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class).invoke(entity, LocalDateTime.now());
            }
        } catch (Exception e) {
            log.error("自动填充字段出错...", e);
            throw new RuntimeException(e);
        }
    }
}
