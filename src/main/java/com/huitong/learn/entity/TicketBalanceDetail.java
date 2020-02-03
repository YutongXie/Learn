package com.huitong.learn.entity;

import java.util.List;

public class TicketBalanceDetail {
    private int id;
    private int ticketBalanceId;
    private String seatType;
    private int coach1;
    private int coach2;
    private int coach3;
    private int coach4;
    private int coach5;
    private int coach6;
    private int coach7;
    private int coach8;
    private int coach9;
    private int coach10;
    private List<TicketBalanceCoachDetail> ticketBalanceCoachDetailList;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getCoach1() {
        return coach1;
    }

    public void setCoach1(int coach1) {
        this.coach1 = coach1;
    }

    public int getCoach2() {
        return coach2;
    }

    public void setCoach2(int coach2) {
        this.coach2 = coach2;
    }

    public int getCoach3() {
        return coach3;
    }

    public void setCoach3(int coach3) {
        this.coach3 = coach3;
    }

    public int getCoach4() {
        return coach4;
    }

    public void setCoach4(int coach4) {
        this.coach4 = coach4;
    }

    public int getCoach5() {
        return coach5;
    }

    public void setCoach5(int coach5) {
        this.coach5 = coach5;
    }

    public int getCoach6() {
        return coach6;
    }

    public void setCoach6(int coach6) {
        this.coach6 = coach6;
    }

    public int getCoach7() {
        return coach7;
    }

    public void setCoach7(int coach7) {
        this.coach7 = coach7;
    }

    public int getCoach8() {
        return coach8;
    }

    public void setCoach8(int coach8) {
        this.coach8 = coach8;
    }

    public int getCoach9() {
        return coach9;
    }

    public void setCoach9(int coach9) {
        this.coach9 = coach9;
    }

    public int getCoach10() {
        return coach10;
    }

    public void setCoach10(int coach10) {
        this.coach10 = coach10;
    }

    public int getTicketBalanceId() {
        return ticketBalanceId;
    }

    public void setTicketBalanceId(int ticketBalanceId) {
        this.ticketBalanceId = ticketBalanceId;
    }

    public List<TicketBalanceCoachDetail> getTicketBalanceCoachDetailList() {
        return ticketBalanceCoachDetailList;
    }

    public void setTicketBalanceCoachDetailList(List<TicketBalanceCoachDetail> ticketBalanceCoachDetailList) {
        this.ticketBalanceCoachDetailList = ticketBalanceCoachDetailList;
    }
}
