package com.huitong.learn.executor;

import com.huitong.learn.context.LearnApplicationContext;
import com.huitong.learn.dao.TicketRecordDAO;
import com.huitong.learn.entity.TicketRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SaveTicketRecordTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SaveTicketRecordTask.class);
    private List<TicketRecord> ticketRecordList;

    public SaveTicketRecordTask(List<TicketRecord> ticketRecordList) {
        this.ticketRecordList = ticketRecordList;
    }

    @Override
    public void run() {
        logger.info("Save Ticket Records -> {}", ticketRecordList);
        TicketRecordDAO ticketRecordDAO = LearnApplicationContext.getBean("ticketRecordDAO");
        ticketRecordDAO.saveNewTickets(ticketRecordList);
        logger.info("Ticket Records has been saved.");
    }
}
