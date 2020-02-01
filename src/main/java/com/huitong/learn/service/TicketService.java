package com.huitong.learn.service;

import com.huitong.learn.dao.TicketBalanceDAO;
import com.huitong.learn.dao.TicketRecordDAO;
import com.huitong.learn.dao.TrainLineDAO;
import com.huitong.learn.entity.*;
import com.huitong.learn.util.TicketRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
    @Autowired
    private TrainLineDAO trainLineDAO;
    @Autowired
    private TicketBalanceDAO ticketBalanceDAO;
    @Autowired
    private TicketRecordDAO ticketRecordDAO;

    @RequestMapping(value = "/ticketService/queryTrainLines", method = RequestMethod.GET)
    public List<TrainLine> queryTrainLines(String startPosition, String destination) {
        if(StringUtils.isBlank(startPosition) || StringUtils.isBlank(destination)) {
            logger.error("Empty input for the start/destination name");
            return new ArrayList<>();
        }

        return trainLineDAO.getTrainLines(startPosition, destination);
    }

    @RequestMapping(value="/ticketService/queryTicketBalance", method = RequestMethod.GET)
    public List<TicketBalance> queryTicketBalance(String startPosition, String destination, String day) {
        if(StringUtils.isBlank(startPosition) || StringUtils.isBlank(destination) || StringUtils.isBlank(day)) {
            logger.error("Empty input for the start/destination name");
            return new ArrayList<>();
        } else {
            logger.info("Query Ticket Balance - start position:{}, destination:{}, day:{}", startPosition, destination, day);
        }
        return ticketBalanceDAO.getTicketBalance(startPosition, destination, day);
    }

    /*
    this will be used for 2nd level check once already get the high level balance information
     */
    @RequestMapping(value="/ticketService/queryTicketBalanceDetail", method = RequestMethod.GET)
    public List<TicketBalanceDetail> queryTicketBalanceDetail(int id) {
        if(id <= 0) {
            return new ArrayList<>();
        } else {
            logger.info("Query ticket balance detail - id:{} ", id);
        }
        return ticketBalanceDAO.getTicketBalanceDetail(id);
    }

    @RequestMapping(value="/ticketService/buyTicket", method = RequestMethod.POST)
    public boolean buyTicket(@RequestBody TicketRequest ticketRequest) {
        if(isNotValidTicketeRequest(ticketRequest)) {
            return false;
        } else {
            logger.info("Buy ticket - ticket request:{}", ticketRequest);
        }

        List<TicketRecord> ticketRecordList = new ArrayList<>();
        for (Ticket ticket : ticketRequest.getTicketList()) {//update TicketBalance

            //1. check if have available seats
            List<TicketBalance> ticketBalanceList = ticketBalanceDAO.getTicketBalance(ticket.getStartPosition(), ticket.getDestination(), ticket.getDay());
            if (!TicketRequestUtil.isSeatAvailable(ticketBalanceList, ticket)) {
                logger.error("BuyTicket - seat not available for ticket request:{}", ticket);
                continue;
            }

            String seatInfo  = ticketBalanceDAO.updateTicketBalance(ticketBalanceList.get(0).getId(), ticket.getSeatType(), 1);
            TicketRecord ticketRecord = new TicketRecord();
            ticketRecord.setRequestId(TicketRequestUtil.generateRequestId());
            String coachNum = seatInfo.substring(0, seatInfo.indexOf("_"));
            String seatNum = seatInfo.substring(seatInfo.indexOf("_") + 1);
            ticketRecord.setCoachNum(Integer.parseInt(coachNum));
            ticketRecord.setSeatNum(seatNum);
            ticketRecord.setStartPosition(ticket.getStartPosition());
            ticketRecord.setDestination(ticket.getDestination());
            ticketRecord.setBuyer(ticketRequest.getUserName());
            ticketRecord.setLineName(ticket.getLineName());
            ticketRecord.setStatus("ACTIVE");
            ticketRecord.setCreateTime(new Date());
            ticketRecord.setPassenger(ticket.getCustomer());
            ticketRecordList.add(ticketRecord);
        }
        //Generate ticket record in TicketRecords
        //TODO: maybe can leave the save ticket function to other component
        // drop the save ticket request to thread pool
        ticketRecordDAO.saveNewTickets(ticketRecordList);
        return true;
    }

    @RequestMapping(value="/ticketService/queryTicketRecords" , method = RequestMethod.GET)
    public List<TicketRecord> queryTicketRecords(String buyerName) {
        if(StringUtils.isBlank(buyerName)) {
            logger.error("queryTicketRecords - Missing buyer name");
            return new ArrayList<>();
        } else {
            logger.info("queryTicketRecords - buyerName:{}", buyerName);
        }
        return ticketRecordDAO.queryTicketRecords(buyerName);
    }

    private boolean isNotValidTicketeRequest(TicketRequest ticketRequest) {
        return !isValidTicketRequest(ticketRequest);
    }

    private boolean isValidTicketRequest(TicketRequest ticketRequest) {
        if(ticketRequest == null) {
            logger.error("Ticket request is null");
            return false;
        }

        if(StringUtils.isBlank(ticketRequest.getUserName())) {
            logger.error("Ticket request - user name is empty");
            return false;
        }

        if(ticketRequest.getTicketList() == null || ticketRequest.getTicketList().size() ==0 ) {
            logger.error("Ticket info missing in Ticket request");
            return false;
        }
        for(Ticket ticket: ticketRequest.getTicketList()) {
            if(StringUtils.isBlank(ticket.getCustomer())) {
                logger.error("Missing customer in Ticket");
                return false;
            }
            if(StringUtils.isBlank((ticket.getSeatType()))) {
                logger.error("Missing seat type in Ticket");
                return false;
            }

            if(StringUtils.isBlank(ticket.getDay())) {
                logger.error("Missing day in Ticket");
                return false;
            }
            if(StringUtils.isBlank(ticket.getLineName())) {
                logger.error("Missing line name in Ticket");
                return false;
            }
        }
        return true;
    }
}
