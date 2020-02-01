package com.huitong.learn.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Ticket {
    private String customer;
    private String seatNum;
    private String seatType;
    private int coachNum;
    private String startPosition;
    private String destination;
    private String lineName;
    private String day;

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

    public int getCoachNum() {
        return coachNum;
    }

    public void setCoachNum(int coachNum) {
        this.coachNum = coachNum;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
