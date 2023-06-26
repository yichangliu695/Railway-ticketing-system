package com.hjnu.model.vo;

import lombok.Data;

/**
 *
 *
 * 用户类  对应前端的个人信息查询
 *
 *在查询个人信息时作为给前端的返回数据
 */
@Data
public class UserInfo {
    private String user_real_name;
    private String user_phone_number;
    private String user_email;
    private String user_type;
    private String user_gender;
    private String user_id_number;
    private String user_address;

    public UserInfo(String user_real_name, String user_phone_number, String user_email, String user_type, String user_gender, String user_id_number, String user_address) {
        this.user_real_name = user_real_name;
        this.user_phone_number = user_phone_number;
        this.user_email = user_email;
        this.user_type = user_type;
        this.user_gender = user_gender;
        this.user_id_number = user_id_number;
        this.user_address = user_address;
    }


}
