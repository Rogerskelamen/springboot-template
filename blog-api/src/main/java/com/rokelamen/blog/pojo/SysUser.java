package com.rokelamen.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysUser {

    // @TableId(type = IdType.ASSIGN_ID) // 默认ID类型
    private Long id;

    private String account;

    private Boolean admin;

    private String avatar;

    private Long createDate;

    private Boolean deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
