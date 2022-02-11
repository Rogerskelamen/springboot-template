package com.rokelamen.blog.config;

import com.rokelamen.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域配置
        registry.addMapping("/**").allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器目标路径
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test");
    }
}
