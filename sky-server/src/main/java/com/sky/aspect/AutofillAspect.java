package com.sky.aspect;

import com.sky.annotation.Autofill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Slf4j
@Component
public class AutofillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.Autofill))")
    public void insertOrUpdateMapperMethod(){
    }

    @Before("insertOrUpdateMapperMethod()")
    public void autofillCommonableField(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("自动填充公共字段");
        Object entity = joinPoint.getArgs()[0];
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Autofill autofillAnnotation =  signature.getMethod().getAnnotation(Autofill.class);
        OperationType operationType = autofillAnnotation.operationType();

        Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
        Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
        setUpdateTime.invoke(entity, LocalDateTime.now());
        setUpdateUser.invoke(entity, BaseContext.getCurrentId());

        if(operationType == OperationType.INSERT){
            Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
            setCreateTime.invoke(entity, LocalDateTime.now());
            setCreateUser.invoke(entity, BaseContext.getCurrentId());
        }

    }
}
