package com.example.test8_11.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//spring生命周期的类  spring再ioc初始化完成以后 会执行这种类setApplicationContext这个方法并且
//注入ioc对象
@Component
public class MyBeanUtil implements ApplicationContextAware {
    private static ApplicationContext myApplicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        myApplicationContext = applicationContext;
    }
    public static <T>T getBean(Class<T> tClass){
       return myApplicationContext.getBean(tClass);
    }
}
