package com.chess.card.api.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.chess.card.api.interceptor.TokenAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 1、继承WebMvcConfigurationSupport
 * 2、实现WebMvcConfigurer
 */
@Configuration
@Component
public class WebConfiguration implements WebMvcConfigurer {
	@Autowired
	private TokenAuthInterceptor tokenAuthInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//诉讼保全工具律师端拦截器
		registry.addInterceptor(tokenAuthInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/webjars/**","/swagger-resources/**","/favicon.ico", "/v2/**","/swagger-ui.html","/doc.html/**");

	}


	@Bean
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
	    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	    javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
	    om.registerModule(javaTimeModule);
        return om;
    }

}
