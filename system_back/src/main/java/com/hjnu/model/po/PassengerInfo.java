package com.hjnu.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;

/**、
 *乘客类  对应数据的passenger表
 *
 */
@Data
@AllArgsConstructor
public class PassengerInfo {
    //用户电话号
    private String user_phone_number;
    //乘客姓名
    private String passenger_real_name;
    //乘客电话
    private String passenger_phone_number;
    //乘客身份证号
    private String passenger_id_number;
    //类型
    private String passenger_type;
    //住址
    private String passenger_address;

}
