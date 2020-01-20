package com.huitong.learn.service;

import com.huitong.learn.dao.TicketBalanceDAO;
import com.huitong.learn.dao.TrainLineDAO;
import com.huitong.learn.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class TicketService {
    @Autowired
    private TrainLineDAO trainLineDAO;
    @Autowired
    private TicketBalanceDAO ticketBalanceDAO;

    @RequestMapping(value = "/ticketService/queryTrainLines", method = RequestMethod.GET)
    public List<TrainLine> queryTrainLines(String startPosition, String destination) {
        if(StringUtils.isBlank(startPosition) || StringUtils.isBlank(destination)) {
            System.out.println("Error input for the start/destination name");
            return new ArrayList<>();
        }

        return trainLineDAO.getTrainLines(startPosition, destination);
    }

    @RequestMapping(value="/ticketService/queryTicketBalance", method = RequestMethod.GET)
    public List<TicketBalance> queryTicketBalance(String startPosition, String destination, String day) {
        if(StringUtils.isBlank(startPosition) || StringUtils.isBlank(destination) || StringUtils.isBlank(day)) {
            System.out.println("Error input for the start/destination name");
            return new ArrayList<>();
        }
        return ticketBalanceDAO.getTicketBalance(startPosition, destination, day);
    }
    @RequestMapping(value="/ticketService/buyTicket", method = RequestMethod.POST)
    public boolean buyTicket(TicketRequest ticketRequest) {
        if(isNotValidTicketeRequest(ticketRequest)) {
            return false;
        }
        //update TicketBalance
        ticketBalanceDAO.updateTicketBalance(ticketRequest.getDay(), ticketRequest.getLineName(),
                ticketRequest.getTicketList().get(0).getSeatType(), ticketRequest.getTicketList().size());
        //Generate ticket record in TicketRecords
        List<TicketRecord> ticketRecordList = new ArrayList<>();
        ticketRequest.getTicketList().forEach(ticket ->{
            TicketRecord ticketRecord = new TicketRecord();
            ticketRecord.setBuyer(ticketRequest.getUserName());
            ticketRecord.setLineName(ticketRequest.getLineName());
            ticketRecord.setStatus("ACITVE");
            ticketRecord.setCreateTime(new Date());
            ticketRecord.setPassenger(ticket.getCustomer());
            ticketRecordList.add(ticketRecord);
        });

        return true;
    }

    private boolean isNotValidTicketeRequest(TicketRequest ticketRequest) {
        return !isValidTicketRequest(ticketRequest);
    }

    private boolean isValidTicketRequest(TicketRequest ticketRequest) {
        if(ticketRequest == null)
            return false;
        if(StringUtils.isBlank(ticketRequest.getDay()))
            return false;
        if(StringUtils.isBlank(ticketRequest.getLineName()))
            return false;
        if(StringUtils.isBlank(ticketRequest.getUserName()))
            return false;

        if(ticketRequest.getTicketList() == null || ticketRequest.getTicketList().size() ==0 )
            return false;
        for(Ticket ticket: ticketRequest.getTicketList()) {
            if(StringUtils.isBlank(ticket.getCustomer()))
                return false;
            if(StringUtils.isBlank((ticket.getSeatType())))
                return false;
        }
        return true;
    }
}
