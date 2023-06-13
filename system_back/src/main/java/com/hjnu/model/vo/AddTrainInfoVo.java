package com.hjnu.model.vo;

import lombok.Data;

/**
 * @author LiuYiChang
 * @date 2023/6/13 11:49
 */
@Data
public class AddTrainInfoVo {
    private String train_arrive_day;

    private String train_carriages;

    private String train_end_station;

    private String train_end_time;

    private String train_number;

    private String train_running_time;

    private String train_running_type;

    private String train_start_station;

    private String train_start_time;

    private String train_type;
}
