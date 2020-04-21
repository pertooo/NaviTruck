package com.example.navitruck.dto;

import com.example.navitruck.dto.abst.AbstractDTO;

public class TruckStatus extends AbstractDTO {

    private int status;
    private String statusStr;
    private String note = null;
    private boolean images = false;
    private boolean done;

    public TruckStatus(){

    }

    public TruckStatus(int status, String statusStr){
        this.status = status;
        this.statusStr = statusStr;
    }

    public TruckStatus(int status, String statusStr, String note, boolean imaged, boolean done){
        this.status = status;
        this.statusStr = statusStr;
        this.note = note;
        this.images = imaged;
        this.done = done;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isImages() {
        return images;
    }

    public void setImages(boolean images) {
        this.images = images;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
