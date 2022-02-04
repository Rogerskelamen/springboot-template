package com.rokelamen.blog.service;

import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.params.LoginParams;

public interface LoginService {

    /**
     * 登录功能
     * @return
     */
    Result login(LoginParams loginParams);
}
