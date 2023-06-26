package com.hjnu.model.po;

import lombok.Data;

@Data
public class SeatInfo {
    private String train_no;
    private String train_number;
    private String carriage_no;
    private String seat_type;
    private int seat_count;

    public SeatInfo(String train_no, String train_number, String carriage_no, String seat_type, int seat_count) {
        this.train_no = train_no;
        this.train_number = train_number;
        this.carriage_no = carriage_no;
        this.seat_type = seat_type;
        this.seat_count = seat_count;
    }

}
