package com.zy.comm.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by zy on 2017/3/31.
 */
@Component
@Lazy(false)
public class SpringContext implements ApplicationContextAware {

    private static  ApplicationContext context = null;

    @Override
    @SuppressWarnings(value="static-access")
    public void setApplicationContext(ApplicationContext applicationContext){
        context = applicationContext;
    }
    public static Object getBean(String name){
        return context.getBean(name);
    }

    public static <T> T getBean(String name , Class<T> claz){
        return context.getBean(name,claz);
    }

    public static <T> T getBean(Class<T> claz){
        return context.getBean(claz);
    }
}
