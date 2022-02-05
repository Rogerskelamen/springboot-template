package com.rokelamen.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rokelamen.blog.mapper.SysUserMapper;
import com.rokelamen.blog.pojo.SysUser;
import com.rokelamen.blog.service.SysUserService;
import com.rokelamen.blog.service.TokenService;
import com.rokelamen.blog.vo.ErrorCode;
import com.rokelamen.blog.vo.LoginUserVo;
import com.rokelamen.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            user = new SysUser();
            user.setNickname("佚名");
        }
        return user;
    }

    /**
     * 通过用户名和账号查找user
     * @param account
     * @param password
     * @return
     */
    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1. token合法性校验
         *  是否为空，解析是否成功 redis是否存在
         * 2. 如果校验失败 返回错误
         * 3. 如果成功，返回对应的结果LoginUserVo
         */
        SysUser sysUser = tokenService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }

        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setNickname(sysUser.getNickname());
        System.out.println(loginUserVo);

        return Result.success(loginUserVo);
    }
}
