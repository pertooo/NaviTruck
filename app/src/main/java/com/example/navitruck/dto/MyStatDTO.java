package com.example.navitruck.dto;

public class MyStatDTO {

    private String title;
    private long weekVal;
    private long monthVal;
    private long totalVal;
    private UnitType unit;

    public enum UnitType {
        Dollar,
        Mile,
        Unit
    }

    public MyStatDTO(String title, long weekVal, long monthVal, long totalVal, UnitType unit){
        this.title = title;
        this.weekVal = weekVal;
        this.monthVal = monthVal;
        this.totalVal = totalVal;
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public long getWeekVal() {
        return weekVal;
    }

    public long getMonthVal() {
        return monthVal;
    }

    public long getTotalVal() {
        return totalVal;
    }

    public UnitType getUnit() {
        return unit;
    }
}
