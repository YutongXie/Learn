package com.huitong.learn.util;

import com.huitong.learn.entity.Ticket;
import com.huitong.learn.entity.TicketBalance;

import java.util.List;
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

    public static boolean isSeatAvailable(List<TicketBalance> ticketBalanceList, Ticket ticket) {
        boolean seatAvailable = true;
        if(ticketBalanceList == null || ticketBalanceList.size() == 0) {
            return false;
        }
        TicketBalance ticketBalance = ticketBalanceList.get(0);
        switch (ticket.getSeatType()) {
            case "A":
                if (ticketBalance.getSeatABalance() > 0) {
                    seatAvailable = true;
                } else {
                    seatAvailable = false;
                }
                break;
            case "B":
                if (ticketBalance.getSeatBBalance() > 0) {
                    seatAvailable = true;
                } else {
                    seatAvailable = false;
                }
                break;
            case "C":
                if (ticketBalance.getSeatCBalance() > 0) {
                    seatAvailable = true;
                } else {
                    seatAvailable = false;
                }
                break;
            case "E":
                if (ticketBalance.getSeatEBalance() > 0) {
                    seatAvailable = true;
                } else {
                    seatAvailable = false;
                }
                break;
            case "F":
                if (ticketBalance.getSeatFBalance() > 0) {
                    seatAvailable = true;
                } else {
                    seatAvailable = false;
                }
                break;
            default:
                break;
        }
        return seatAvailable;
    }

    public static void main(String[] args) {
        String uuid = TicketRequestUtil.generateRequestId();
        System.out.println(uuid);
    }
}
