package com.huitong.learn.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class TicketRequest {
    private String userName;

    private List<Ticket> ticketList;

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
