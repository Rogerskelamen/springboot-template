package com.rokelamen.blog.service;

import com.rokelamen.blog.pojo.SysUser;
import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.UserVo;

public interface SysUserService {

    /**
     * 根据
     * @param id
     * @return
     */
    UserVo findUserVoById(Long id);

    /**
     * 通过id查询User(Author)
     * @param id 用户id
     * @return  用户信息
     */
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token 前端传过来的token
     * @return  用户信息
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param user
     */
    void save(SysUser user);
}
