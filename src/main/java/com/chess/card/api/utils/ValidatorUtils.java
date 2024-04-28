package com.chess.card.api.utils;

import com.chess.card.api.exception.BuziException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
public class ValidatorUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void parameterValidation(Object param,Class<?>... groups){
        if(param==null){
            log.error("parameterValidation param=null");
            throw new BuziException("必填参数为空");
        }
        Set<ConstraintViolation<Object>> validateResult = validator.validate(param, groups);
        if(CollectionUtils.isNotEmpty(validateResult)){
            for (ConstraintViolation<Object> error : validateResult) {
                throw new BuziException(error.getMessage());
            }
        }
    }


    public static void parameterValidation(Object param){
        if(param==null){
            log.error("parameterValidation param=null");
            throw new BuziException("必填参数为空");
        }

        Set<ConstraintViolation<Object>> validateResult = validator.validate(param);
        if(CollectionUtils.isNotEmpty(validateResult)){
            for (ConstraintViolation<Object> error : validateResult) {
                throw new BuziException(error.getMessage());
            }
        }
    }
}
