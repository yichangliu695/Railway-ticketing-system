package com.hjnu.model.vo;

import lombok.Data;

import java.util.Objects;

@Data
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

    public GetAllOrderList(String order_id, String passenger_real_name, String train_number, String start_station_name, String end_station_name, String carriage_no, String seat_type, String seat_no, String start_date, String start_time, String order_status, String passenger_phone_number, String passenger_id_number, String order_money) {
        this.order_id = order_id;
        this.passenger_real_name = passenger_real_name;
        this.train_number = train_number;
        this.start_station_name = start_station_name;
        this.end_station_name = end_station_name;
        this.carriage_no = carriage_no;
        this.seat_type = seat_type;
        this.seat_no = seat_no;
        this.start_date = start_date;
        this.start_time = start_time;
        this.order_status = order_status;
        this.passenger_phone_number = passenger_phone_number;
        this.passenger_id_number = passenger_id_number;
        this.order_money = order_money;
    }
    public boolean equals(Object obj){
        GetAllOrderList o=(GetAllOrderList) obj;
        if (Objects.equals(this.getOrder_id(), o.getOrder_id())){
            return true;
        }
        return false;
    }

}
