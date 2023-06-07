package com.hjnu.model.po;


import lombok.Data;

/**
 *
 *
 * 列车信息类
 * 与数据库的train_info表相对应
 *
 */
@Data
public class TrainInfo {
    private String train_no;
    private String train_number;
    private String train_type;
    private String train_carriages;
    private String train_start_station;
    private String train_end_station;
    private String train_start_time;
    private String train_end_time;
    private String train_arrive_day;
    private String train_running_time;

    private String train_running_type;

    public TrainInfo(String train_no, String train_number, String train_type, String train_carriages, String train_start_station, String train_end_station, String train_start_time, String train_end_time, String train_arrive_day, String train_running_time, String train_running_type) {
        this.train_no = train_no;
        this.train_number = train_number;
        this.train_type = train_type;
        this.train_carriages = train_carriages;
        this.train_start_station = train_start_station;
        this.train_end_station = train_end_station;
        this.train_start_time = train_start_time;
        this.train_end_time = train_end_time;
        this.train_arrive_day = train_arrive_day;
        this.train_running_time = train_running_time;
        this.train_running_type = train_running_type;
    }

    }
