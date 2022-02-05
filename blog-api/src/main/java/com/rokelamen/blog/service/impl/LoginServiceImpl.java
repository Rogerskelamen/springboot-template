package com.rokelamen.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.rokelamen.blog.pojo.SysUser;
import com.rokelamen.blog.service.LoginService;
import com.rokelamen.blog.service.SysUserService;
import com.rokelamen.blog.utils.JWTUtils;
import com.rokelamen.blog.vo.ErrorCode;
import com.rokelamen.blog.vo.Result;
import com.rokelamen.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String slat = "123456Rogers%%Code";

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1. 检查参数是否合法
         * 2. 根据用户名和密码去user表中查询 是否存在
         * 3. 如果不存在 登录失败
         * 4. 如果存在，使用jwt生成 token 返回给前端
         * 5. token放入redis中，redis token: user信息 设置过期时间
         * （登录认证的时候 先去认证token字符串是否合法，去redis认证是否存在）
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        // 加密password，因为数据库中的密码都是加密存放的
        password = DigestUtils.md5Hex(password + slat);
        SysUser user = sysUserService.findUser(account, password);

        if (user == null) {
            return Result.fail(ErrorCode.ACCOUNT_ERROR.getCode(), ErrorCode.ACCOUNT_ERROR.getMsg());
        }

        String token = JWTUtils.createToken(user.getId());

        // 在redis缓存中存储token信息，value值是整个user信息
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }
}
