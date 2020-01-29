package com.huitong.learn.dao;

import com.huitong.learn.entity.Ticket;
import com.huitong.learn.entity.TicketRecord;

import java.util.List;

public interface TicketRecordDAO {
    boolean saveNewTickets(List<TicketRecord> ticketRecordList);
}
