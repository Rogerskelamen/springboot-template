package com.rokelamen.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApp {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(BlogApp.class, args);
    }
}
