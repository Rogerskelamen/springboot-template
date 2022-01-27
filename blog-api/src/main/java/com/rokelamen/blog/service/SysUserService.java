package com.rokelamen.blog.service;

import com.rokelamen.blog.pojo.SysUser;

public interface SysUserService {
    /**
     * 通过id查询User(Author)
     * @param id 用户id
     * @return  用户信息
     */
    SysUser findUserById(Long id);
}
