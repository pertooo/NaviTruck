package com.example.navitruck.dto;

import com.example.navitruck.dto.abst.AbstractDTO;

public class TruckStatus extends AbstractDTO {

    public TruckStatus(){

    }

    private int status;
    private String zipCode;
    private String note;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
