package com.hjnu.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 *
 * 登陆注册 时
 * 数据库查询的返回结果
 * 用户查询用户是否注册
 * 是否重复注册过用户
 */

@Data
@AllArgsConstructor
public class UserLogin {
    private String user_phone_number;

    private String user_password;
}
