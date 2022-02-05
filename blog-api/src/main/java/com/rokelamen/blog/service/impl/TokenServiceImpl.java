package com.rokelamen.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.rokelamen.blog.pojo.SysUser;
import com.rokelamen.blog.service.TokenService;
import com.rokelamen.blog.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        // 解析token, 如果解析错误直接返回
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }

        // 验证redis中是否有这个token(是否过期)
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }
}
