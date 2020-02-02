package com.huitong.learn.dao;

import com.huitong.learn.entity.TicketBalance;
import com.huitong.learn.entity.TicketBalanceCoachDetail;
import com.huitong.learn.entity.TicketBalanceDetail;

import java.util.List;

public interface TicketBalanceDAO {
    List<TicketBalance> getTicketBalance(String startPosition, String destination, String day);
    List<TicketBalanceDetail> getTicketBalanceDetail(int ticketBalanceId);
    List<TicketBalanceCoachDetail> getTicketBalanceCoachDetail(int ticketBalanceId);
    String updateTicketBalance(int id, String type, int count);
}
