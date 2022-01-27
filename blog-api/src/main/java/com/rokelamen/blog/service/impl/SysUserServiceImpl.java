package com.rokelamen.blog.service.impl;

import com.rokelamen.blog.mapper.SysUserMapper;
import com.rokelamen.blog.pojo.SysUser;
import com.rokelamen.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            user = new SysUser();
            user.setNickname("佚名");
        }
        return user;
    }
}
