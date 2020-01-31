package com.huitong.learn.util;

import java.util.UUID;

public class TicketRequestUtil {

    public static String generateRequestId() {
       return UUID.randomUUID().toString();
    }


    /*
    coach_seatNum pattern: 10_15F
     */
    public static String generateCoachNumAndSeatNum(String lineName, String seatType) {
        int coachNum = 0;
        //1. check any available seats with specific seat type in all coach

        //2. if no match, then do random assign seat

        return "10_15F";
    }

    public static void main(String[] args) {
        String uuid = TicketRequestUtil.generateRequestId();
        System.out.println(uuid);
    }
}
