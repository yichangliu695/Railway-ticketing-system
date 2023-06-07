package com.hjnu.model.po;

import lombok.Data;

import java.util.Date;

/**
 * 用户类  对应与数据库的用户表user
 */
@Data
public class User {
    private String user_phone_number;
    private String user_password;
    private String user_email;
    private String user_real_name;
    private int user_type;
    private String user_id_number;
    private int user_gender;
    private String user_address;

    public User(String user_phone_number, String user_password, String user_email , String user_real_name
            , int user_type ,String user_id_number
            ,int user_gender ,String  user_address)
    {
        this.user_phone_number = user_phone_number;
        this.user_password  = user_password;
        this.user_email = user_email;
        this.user_real_name  = user_real_name;
        this.user_type = user_type;
        this.user_id_number = user_id_number;
        this.user_gender = user_gender;
        this.user_address = user_address;
    }

}
