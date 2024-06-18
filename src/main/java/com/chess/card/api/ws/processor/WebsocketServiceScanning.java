package com.chess.card.api.ws.processor;

import cn.hutool.core.util.ReflectUtil;
import com.chess.card.api.ws.annotation.WebSocketApiHandler;
import com.chess.card.api.ws.annotation.WebSocketApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class WebsocketServiceScanning implements ApplicationContextAware, BeanPostProcessor {

    private Map<String,Object> serviceCacheMap = new HashMap<>();

    private Map<String,Method> serviceMethodCacheMap = new HashMap<>();

    private ApplicationContext applicationContext;

//    public <T>  T invokeMethod(String methodName){
//       return this.invokeMethod(methodName, (Object) null);
//    }
//
//    public <T>  T invokeMethod(String methodName,Object...params){
//        return this.invokeMethod(methodName, Arrays.asList(params));
//    }

    public <T>  T invokeMethod(String methodName, Object...params){
        if(StringUtils.isBlank(methodName)){
            throw new IllegalArgumentException("方法不能为空");
        }

        if(!serviceMethodCacheMap.containsKey(methodName)){
            log.error("方法不存在 methodName={}",methodName);
            throw new IllegalArgumentException("方法不存在");
        }
        Object target = serviceCacheMap.get(methodName);

        Method method = serviceMethodCacheMap.get(methodName);
        if(params==null || params.length == 0){
            return (T)ReflectionUtils.invokeMethod(method,target);
        }
       // Object[] args = params.stream().toArray(Object[]::new);

        return ReflectUtil.invoke(target,method,params);
    }


    private void scanCustomAnnotatedBeans() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(WebSocketApiService.class);
        for (Object bean : beans.values()) {
            Class<?> beanClass = bean.getClass();
            if (beanClass.isAnnotationPresent(WebSocketApiService.class)) {
                WebSocketApiService annotation = beanClass.getAnnotation(WebSocketApiService.class);
                Method[] methods = beanClass.getDeclaredMethods();
                String serviceName = beanClass.getName();
                serviceCacheMap.put(serviceName,bean);
                for (Method method : methods) {
                    if (AnnotationUtils.findAnnotation(method, WebSocketApiHandler.class) != null) {
                        WebSocketApiHandler webSocketApiHandler = method.getAnnotation( WebSocketApiHandler.class);
                        String methodName =  StringUtils.defaultIfBlank(webSocketApiHandler.value(),method.getName());
                        serviceMethodCacheMap.put(methodName,method);
                        serviceCacheMap.put(methodName,bean);
                    }
                }
            }
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.scanCustomAnnotatedBeans();
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
