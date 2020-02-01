package com.huitong.learn.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
@Component
public class TicketRecordExecutor extends ScheduledThreadPoolExecutor {

    @Autowired
    public TicketRecordExecutor(int ticketRecordCorePoolSize) {
        super(ticketRecordCorePoolSize, new CallerRunsPolicy());
        System.out.println("pool size is:"  + ticketRecordCorePoolSize);
    }
}
