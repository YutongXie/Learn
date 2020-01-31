package com.huitong.learn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huitong.learn.entity.Ticket;
import com.huitong.learn.entity.TicketRequest;

import java.util.ArrayList;
import java.util.List;

public class JsonUitl {


    public String ticketRequestToJson (TicketRequest ticketRequest) {
        return JSON.toJSONString(ticketRequest);
    }

    public static void main(String[] args) {
        JsonUitl jsonUitl = new JsonUitl();
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setUserName("test");
        Ticket ticket = new Ticket();
        ticket.setCoachNum(12);
        ticket.setCustomer("p-1");
        ticket.setDay("2020-01-20");
        ticket.setStartPosition("DALIAN");
        ticket.setDestination("PANJIN");
        ticket.setLineName("D35");
        ticket.setSeatType("F");
        ticket.setSeatNum("16F");
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);
        Ticket ticket1 = new Ticket();
        ticket1.setCoachNum(12);
        ticket1.setCustomer("p-1");
        ticket1.setDay("2020-01-20");
        ticket1.setStartPosition("DALIAN");
        ticket1.setDestination("PANJIN");
        ticket1.setLineName("D35");
        ticket1.setSeatType("F");
        ticket1.setSeatNum("16F");
        ticketList.add(ticket1);
        ticketRequest.setTicketList(ticketList);
        String json = jsonUitl.ticketRequestToJson(ticketRequest);
        System.out.println(json);
    }
}
