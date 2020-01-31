package com.huitong.learn.service;

import com.huitong.learn.dao.TicketBalanceDAO;
import com.huitong.learn.dao.TicketRecordDAO;
import com.huitong.learn.dao.TrainLineDAO;
import com.huitong.learn.entity.*;
import com.huitong.learn.util.TicketRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    private TicketRecordDAO ticketRecordDAO;

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

    /*
    this will be used for 2nd level check once already get the high level balance information
     */
    @RequestMapping(value="/ticketService/queryTicketBalanceDetail", method = RequestMethod.GET)
    public List<TicketBalanceDetail> queryTicketBalanceDetail(int id) {
        if(id <= 0)
            return new ArrayList<>();
        return ticketBalanceDAO.getTicketBalanceDetail(id);
    }

    @RequestMapping(value="/ticketService/buyTicket", method = RequestMethod.POST)
    public boolean buyTicket(@RequestBody TicketRequest ticketRequest) {
        if(isNotValidTicketeRequest(ticketRequest)) {
            return false;
        }

        List<TicketRecord> ticketRecordList = new ArrayList<>();
        for (Ticket ticket : ticketRequest.getTicketList()) {//update TicketBalance

            //1. check if have available seats
            boolean seatAvailable = true;
            List<TicketBalance> ticketBalanceList = ticketBalanceDAO.getTicketBalance(ticket.getStartPosition(), ticket.getDestination(), ticket.getDay());
            if(ticketBalanceList == null || ticketBalanceList.size() == 0)
                return false;

            String seatBlance = "";
            switch (ticket.getSeatType()) {
                case "A":
                    if (ticketBalanceList.get(0).getSeatABalance() > 0) {
                        seatAvailable = true;
                    } else {
                        seatAvailable = false;
                    }
                    break;
                case "B":
                    if (ticketBalanceList.get(0).getSeatBBalance() > 0) {
                        seatAvailable = true;
                    } else {
                        seatAvailable = false;
                    }
                    break;
                case "C":
                    if (ticketBalanceList.get(0).getSeatCBalance() > 0) {
                        seatAvailable = true;
                    } else {
                        seatAvailable = false;
                    }
                    break;
                case "E":
                    if (ticketBalanceList.get(0).getSeatEBalance() > 0) {
                        seatAvailable = true;
                    } else {
                        seatAvailable = false;
                    }
                    break;
                case "F":
                    if (ticketBalanceList.get(0).getSeatFBalance() > 0) {
                        seatAvailable = true;
                    } else {
                        seatAvailable = false;
                    }
                    break;
                default:
                    break;
            }

            if (!seatAvailable) {
                continue;
            }

            String seatInfo  = ticketBalanceDAO.updateTicketBalance(ticketBalanceList.get(0).getId(), ticket.getSeatType(), 1);
            TicketRecord ticketRecord = new TicketRecord();
            ticketRecord.setId(100);
            ticketRecord.setRequestId(TicketRequestUtil.generateRequestId());

            String coachNum = seatInfo.substring(0, seatInfo.indexOf("_"));
            String seatNum = seatInfo.substring(seatInfo.indexOf("_") + 1);
            ticketRecord.setCoachNum(Integer.parseInt(coachNum));
            ticketRecord.setSeatNum(seatNum);
            ticketRecord.setStartPosition(ticket.getStartPosition());
            ticketRecord.setDestination(ticket.getDestination());
            ticketRecord.setBuyer(ticketRequest.getUserName());
            ticketRecord.setLineName(ticket.getLineName());
            ticketRecord.setStatus("ACITVE");
            ticketRecord.setCreateTime(new Date());
            ticketRecord.setPassenger(ticket.getCustomer());
            ticketRecordList.add(ticketRecord);
        }
        //Generate ticket record in TicketRecords
        ticketRecordDAO.saveNewTickets(ticketRecordList);
        return true;
    }

    @RequestMapping(value="/ticketService/queryTicketRecords" , method = RequestMethod.GET)
    public List<TicketRecord> queryTicketRecords(String buyerName) {
        if(StringUtils.isBlank(buyerName))
            return new ArrayList<>();
        return ticketRecordDAO.queryTicketRecords(buyerName);
    }

    private boolean isNotValidTicketeRequest(TicketRequest ticketRequest) {
        return !isValidTicketRequest(ticketRequest);
    }

    private boolean isValidTicketRequest(TicketRequest ticketRequest) {
        if(ticketRequest == null)
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

            if(StringUtils.isBlank(ticket.getDay()))
                return false;
            if(StringUtils.isBlank(ticket.getLineName()))
                return false;
        }
        return true;
    }
}
