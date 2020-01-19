package com.huitong.learn.entity;

public class TicketBalance {
    private TrainLine trainLine;
    //Seat A, B, C, E, F
    private int seatABalance;
    private int seatBBalance;
    private int seatCBalance;
    private int seatEBalance;
    private int seatFBalance;

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
}
