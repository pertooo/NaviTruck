package com.example.navitruck.dto;

import java.io.Serializable;

public class Task implements Serializable {

    private String addressFrom;
    private String addressTo;

    public Task(String addressFrom, String addressTo){
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }
}
