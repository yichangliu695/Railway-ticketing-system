package com.hjnu.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class GetAllOrderList {
    private String order_id;
    private String passenger_real_name;
    private String train_number;
    private String start_station_name;
    private String end_station_name;
    private String carriage_no;
    private String seat_type;
    private String seat_no;
    private String start_date;
    private String start_time;
    private String order_status;
    private String passenger_phone_number;
    private String passenger_id_number;
    private String order_money;
    private String train_no;

    public boolean equals(Object obj){
        GetAllOrderList o=(GetAllOrderList) obj;
        return Objects.equals(this.getOrder_id(), o.getOrder_id());
    }

}
