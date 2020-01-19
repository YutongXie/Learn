package com.huitong.learn.dao;

import com.huitong.learn.entity.TicketBalance;

import java.util.List;

public interface TicketBalanceDAO {
    List<TicketBalance> getTicketBalance(String startPosition, String destination);
}
