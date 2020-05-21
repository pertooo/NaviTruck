package com.example.navitruck.dto;

import java.io.Serializable;

public class Task implements Serializable {

    private long id;

    private String pickUpDate;
    private String deliveryDate;
    private String pickUpAt;
    private String deliveryTo;

    private int miles;
    private int pieces;
    private int weight;
    private String dims;

    public Task(long id, String pickUpDate, String deliveryDate, String pickUpAt, String deliveryTo, int miles, int pieces, int weight, String dims) {
        this.id = id;
//        this.pickUpDate = pickUpDate;
//        this.deliveryDate = deliveryDate;
        this.pickUpAt = pickUpAt;
        this.deliveryTo = deliveryTo;
        this.miles = miles;
        this.pieces = pieces;
        this.weight = weight;
        this.dims = dims;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPickUpAt() {
        return pickUpAt;
    }

    public void setPickUpAt(String pickUpAt) {
        this.pickUpAt = pickUpAt;
    }

    public String getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        this.deliveryTo = deliveryTo;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDims() {
        return dims;
    }

    public void setDims(String dims) {
        this.dims = dims;
    }
}
