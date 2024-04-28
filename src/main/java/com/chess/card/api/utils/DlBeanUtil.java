package com.chess.card.api.utils;



import com.chess.card.api.annotation.Signature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class DlBeanUtil {

    public static <T,V> T beanCopyProperties(V v,T t){
        if(v==null){
            return null;
        }
        BeanUtils.copyProperties(v,t);
        return t;
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static  <T,V> T copyPropertiesIgnoreNull(V v,T t){
        BeanUtils.copyProperties(v, t, getNullPropertyNames(v));
        return t;
    }


    public static String[] getOnlineFiles (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        Field[] fields = source.getClass().getDeclaredFields();
        for(Field field:fields){
            if (!field.isAnnotationPresent(Signature.class)) {
                emptyNames.add(field.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static  <T,V> T copyOnlineData(V v,T t){
        BeanUtils.copyProperties(v, t, getOnlineFiles(v));
        return t;
    }
}
