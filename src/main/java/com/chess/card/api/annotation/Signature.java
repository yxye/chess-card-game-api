package com.chess.card.api.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented

/**
 * 拦截执行函数，精选tokens授权验证
 */
public @interface Signature {

    String value() default "";
    String description() default "加入到签名的字段";
}