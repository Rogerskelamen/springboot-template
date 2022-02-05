package com.rokelamen.blog.service;

import com.rokelamen.blog.pojo.SysUser;

public interface TokenService {

    /**
     * 检查token合法性
     * @param token
     * @return
     */
    SysUser checkToken(String token);
}
