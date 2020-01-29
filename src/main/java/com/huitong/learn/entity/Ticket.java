package com.huitong.learn.entity;

public class Ticket {
    private String customer;
    private String seatNum;
    private String seatType;
    private String coachNum;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getCoachNum() {
        return coachNum;
    }

    public void setCoachNum(String coachNum) {
        this.coachNum = coachNum;
    }
}
