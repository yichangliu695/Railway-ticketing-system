package com.hjnu.model.vo;

public class TrainNumberData {
    private String value;
    private String address;

    public TrainNumberData(String value, String address) {
        this.value = value;
        this.address = address;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
