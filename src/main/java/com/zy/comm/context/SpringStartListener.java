package com.zy.comm.context;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by zy on 2017/3/31.
 */
@Component
@Lazy(false)
public class SpringStartListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e){
        int aa = 0;
    }

}
