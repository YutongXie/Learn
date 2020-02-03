package com.huitong.learn.util;

import com.huitong.learn.context.LearnApplicationContext;
import com.huitong.learn.dao.TicketBalanceDAO;
import com.huitong.learn.entity.TicketBalance;
import com.huitong.learn.entity.TicketBalanceDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.krb5.internal.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TicketBalanceUtil {
    private static final Logger logger = LoggerFactory.getLogger(TicketBalanceUtil.class);
    /**
     * Time range: one month (30 days)
     */
    public HashMap<String, TicketBalance> getAllActiveTicketBalance(int range) {
        logger.info("Initial Ticket Balance Map...");
        HashMap<String, TicketBalance> ticketBalanceHashMap = new HashMap<>();
        TicketBalanceDAO ticketBalanceDAO = LearnApplicationContext.getBean("ticketBalanceDAO");
        List<TicketBalance> ticketBalanceList = ticketBalanceDAO.getAllActiveTicketBalance(1);
        String ticketBalanceKey = "lineName|day";
        for (TicketBalance ticketBalance : ticketBalanceList) {
            ticketBalanceKey = ticketBalance.getTrainLine().getLineName() + "|" + ticketBalance.getDay();
            if (ticketBalanceHashMap.containsKey(ticketBalanceKey)) {
                TicketBalance existTicketBalance = ticketBalanceHashMap.get(ticketBalanceKey);
                boolean isNewTicketBalanceDetail = true;
                for(TicketBalanceDetail ticketBalanceDetail : existTicketBalance.getTicketBalanceDetailList()) {
                    if (ticketBalanceDetail.getId() == ticketBalance.getTicketBalanceDetailList().get(0).getId()) {
                        // means need to merge TicketBalanceCoachDetail
                        ticketBalanceDetail.getTicketBalanceCoachDetailList().addAll(ticketBalance.getTicketBalanceDetailList().get(0).getTicketBalanceCoachDetailList());
                        isNewTicketBalanceDetail = false;
                    }
                }
                if(isNewTicketBalanceDetail) {
                    // means need to merge TicketBalanceDetail
                    existTicketBalance.getTicketBalanceDetailList().addAll(ticketBalance.getTicketBalanceDetailList());
                }
            } else {
                ticketBalanceHashMap.put(ticketBalanceKey, ticketBalance);
            }
        }
        logger.info("Initial Ticket Balance Map complete. range:{}, size:{}", range, ticketBalanceHashMap.size());
        return ticketBalanceHashMap;
    }
}
