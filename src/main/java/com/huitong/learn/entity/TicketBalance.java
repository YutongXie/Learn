package com.huitong.learn.entity;

import java.util.Date;

public class TicketBalance {
    private int id;
    private TrainLine trainLine;
    //Seat A, B, C, E, F
    private int seatABalance;
    private int seatBBalance;
    private int seatCBalance;
    private int seatEBalance;
    private int seatFBalance;
    private String day;

    public TrainLine getTrainLine() {
        return trainLine;
    }

    public void setTrainLine(TrainLine trainLine) {
        this.trainLine = trainLine;
    }

    public int getSeatABalance() {
        return seatABalance;
    }

    public void setSeatABalance(int seatABalance) {
        this.seatABalance = seatABalance;
    }

    public int getSeatBBalance() {
        return seatBBalance;
    }

    public void setSeatBBalance(int seatBBalance) {
        this.seatBBalance = seatBBalance;
    }

    public int getSeatCBalance() {
        return seatCBalance;
    }

    public void setSeatCBalance(int seatCBalance) {
        this.seatCBalance = seatCBalance;
    }

    public int getSeatEBalance() {
        return seatEBalance;
    }

    public void setSeatEBalance(int seatEBalance) {
        this.seatEBalance = seatEBalance;
    }

    public int getSeatFBalance() {
        return seatFBalance;
    }

    public void setSeatFBalance(int seatFBalance) {
        this.seatFBalance = seatFBalance;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
