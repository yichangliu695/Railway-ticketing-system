package com.hjnu.model.po;


import lombok.Data;

/**
 * 订单信息
 *
 * 对应订单表
 */
@Data
public class OrderList {

    private String user_phone_number;
    private String passenger_phone_number;
    private String passenger_id_number;
    private String train_no;
    private String start_station_no;
    private String start_station_name;
    private String end_station_no;
    private String end_station_name;
    private String carriage_no;
    private String seat_no;
    private String order_money;
    private String order_create_time;
    private String order_status;
    private String train_start_date;

    public OrderList(String user_phone_number, String passenger_phone_number, String passenger_id_number, String train_no, String start_station_no, String start_station_name, String end_station_no, String end_station_name, String carriage_no, String seat_no, String order_money, String order_create_time, String order_status, String train_start_date) {
        this.user_phone_number = user_phone_number;
        this.passenger_phone_number = passenger_phone_number;
        this.passenger_id_number = passenger_id_number;
        this.train_no = train_no;
        this.start_station_no = start_station_no;
        this.start_station_name = start_station_name;
        this.end_station_no = end_station_no;
        this.end_station_name = end_station_name;
        this.carriage_no = carriage_no;
        this.seat_no = seat_no;
        this.order_money = order_money;
        this.order_create_time = order_create_time;
        this.order_status = order_status;
        this.train_start_date = train_start_date;
    }

}
