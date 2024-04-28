package com.chess.card.api.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented

/**
 * 拦截执行函数，精选tokens授权验证
 */
public @interface UnLogin {

    String value() default "";
    String description() default "不需要登录接口";
}
