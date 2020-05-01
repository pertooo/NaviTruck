package com.example.navitruck.dto;

import android.app.Activity;

public class ActivityDTO {

    private String text;
    private String date;

    public ActivityDTO(String text, String date){
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
