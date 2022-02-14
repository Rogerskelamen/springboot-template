package com.rokelamen.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2     // 开启swagger2
public class SwaggerConfig {

    // 配置Swagger的Docket的bean实例
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 指定要扫描的包
                // basePackage: 指定要扫描的包
                // any ： 扫描全部
                // none : 不扫描
                // withClassAnnotation: 扫描类上的注解
                // withMethodAnnotation: 扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.rokelamen.blog.controller"))
                // path: 过滤什么路径
                // .paths(PathSelectors.ant("/articles/**"))
                .build()
                .groupName("rokelamen");
    }
}
