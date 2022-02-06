package com.rokelamen.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rokelamen.blog.mapper.SysUserMapper;
import com.rokelamen.blog.pojo.SysUser;
import com.rokelamen.blog.service.SysUserService;
import com.rokelamen.blog.service.TokenService;
import com.rokelamen.blog.vo.ErrorCode;
import com.rokelamen.blog.vo.LoginUserVo;
import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.UserVo;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            user = new SysUser();
            user.setId(1L);
            user.setAvatar("");
            user.setNickname("账号已注销");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            user = new SysUser();
            user.setNickname("账号已注销");
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

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser user) {
        // 保存用户 id会自动生成
        // 这个地方 默认生成的id是分布式id 雪花算法
        // mybatis-plus
        sysUserMapper.insert(user);
    }
}
