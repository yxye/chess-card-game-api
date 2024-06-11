package com.chess.card.api.config;

import com.chess.card.api.exception.BuziException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aspect
@Component
public class ParamsCheckAspect {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

 
    // 定义接口参数校验切入点
    @Pointcut("@annotation(org.springframework.validation.annotation.Validated))")
    private void validateMethod() {
    }
    
    @Before("validateMethod()")
    public void before(JoinPoint joinPoint) throws BuziException {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Validated annotation = method.getAnnotation(Validated.class);
        Set<ConstraintViolation<Object>> constraintViolations = new HashSet<>();
        // 执行方法参数的校验
        for(Object param:args){
            constraintViolations.addAll(validator.validate(param, annotation.value()));
        }
       //Set<ConstraintViolation<Object>> constraintViolations = validator.forExecutables().validateParameters(joinPoint.getThis(), method, args, annotation.value());
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<Object> error : constraintViolations) {
            messages.add(error.getMessage());
        }
        if(!messages.isEmpty()){
            throw new BuziException(messages.get(0));
        }
    }
    
}