package com.huitong.learn.service;

import com.huitong.learn.dao.TicketBalanceDAO;
import com.huitong.learn.dao.TrainLineDAO;
import com.huitong.learn.entity.TicketBalance;
import com.huitong.learn.entity.TrainLine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public List<TicketBalance> queryTicketBalance(String startPosition, String destination) {
        if(StringUtils.isBlank(startPosition) || StringUtils.isBlank(destination)) {
            System.out.println("Error input for the start/destination name");
            return new ArrayList<>();
        }
        return ticketBalanceDAO.getTicketBalance(startPosition, destination);
    }
}
