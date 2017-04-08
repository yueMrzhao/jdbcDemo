package com.zy.comm.intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zy on 2017/4/1.
 */
@Configuration
public class IntercepterRegister extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册spring url拦截器
        registry.addInterceptor(new MyIntercepter()).addPathPatterns("/getList/**");
    }
}
