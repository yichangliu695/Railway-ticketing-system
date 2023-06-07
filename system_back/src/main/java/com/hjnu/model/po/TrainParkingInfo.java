package com.hjnu.model.po;

import lombok.Data;

/**
 * 列车时刻表类  数据库的train_parking_info表
 * 存储列车的时刻信息
 *
 */
@Data
public class TrainParkingInfo {

    private String train_number;
    private String arrive_day_str;
    private String start_time;
    private String arrive_time;
    private String running_time;
    private String station_no;
    private String station_name;

    public TrainParkingInfo( String station_no, String station_name,String train_number, String start_time, String arrive_time, String running_time,String arrive_day_str) {
        this.train_number = train_number;
        this.arrive_day_str = arrive_day_str;
        this.start_time = start_time;
        this.arrive_time = arrive_time;
        this.running_time = running_time;
        this.station_no = station_no;
        this.station_name = station_name;
    }

}
