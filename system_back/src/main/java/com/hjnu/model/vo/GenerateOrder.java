package com.hjnu.model.vo;

import com.hjnu.model.po.PassengerInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 生成订单
 *
 * @author LiuYiChang
 * @date 2023/6/14 15:09
 */
@Data
@AllArgsConstructor
public class GenerateOrder {

    //车次id
    String train_number;

    //订单价格
    String order_money;

    //支付状态,已支付，未支付
    String order_status;

    //座位类型
    String seat_type;

    //乘客
    List<PassengerInfo> passenger_data;

}
