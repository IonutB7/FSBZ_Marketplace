package com.marketplace.fsbz_marketplace.model;

import java.sql.Timestamp;

public class Ticket {
    private int ticketId;
    private String sendByUser;
    private String title;
    private String type;
    private String content;
    private Timestamp sendDate;
    private String status;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getSendByUser() {
        return sendByUser;
    }

    public void setSendByUser(String sendByUser) {
        this.sendByUser = sendByUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sentDate) {
        this.sendDate = sentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
