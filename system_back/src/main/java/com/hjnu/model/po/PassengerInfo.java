package com.hjnu.model.po;

import lombok.Data;

/**、
 *乘客类   对应数据的passenger表
 *
 *提供get和set方法已经构造方法
 *
 */
@Data
public class PassengerInfo {
    private String user_phone_number;
    private String passenger_real_name;
    private String passenger_phone_number;
    private String passenger_id_number;
    private String passenger_type;
    private String passenger_address;

    public PassengerInfo(String user_phone_number, String passenger_real_name, String passenger_phone_number, String passenger_id_number, String passenger_type, String passenger_address) {
        this.user_phone_number = user_phone_number;
        this.passenger_real_name = passenger_real_name;
        this.passenger_phone_number = passenger_phone_number;
        this.passenger_id_number = passenger_id_number;
        this.passenger_type = passenger_type;
        this.passenger_address = passenger_address;
    }

    public PassengerInfo(String passenger_real_name, String passenger_phone_number, String passenger_id_number, String passenger_type, String passenger_address) {
        this.passenger_real_name = passenger_real_name;
        this.passenger_phone_number = passenger_phone_number;
        this.passenger_id_number = passenger_id_number;
        this.passenger_type = passenger_type;
        this.passenger_address = passenger_address;
    }

   }
