/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */
package cn.itgardener.nefu.library;

import cn.itgardener.nefu.library.web.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author CMY
 * @date 2018/11/17
 * @since : Java 8
 */

@Configuration
public class SpringBootWebMvcConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    @Autowired
    public SpringBootWebMvcConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
    }
}

