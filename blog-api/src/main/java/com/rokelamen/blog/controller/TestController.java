package com.rokelamen.blog.controller;

import com.rokelamen.blog.pojo.SysUser;
import com.rokelamen.blog.utils.UserThreadLocal;
import com.rokelamen.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @RequestMapping
    public Result test() {
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
