package com.marketplace.fsbz_marketplace.model;

import java.util.ArrayList;

public final class TicketListHolder {

    private ArrayList<Ticket> ticketList;
    private final static com.marketplace.fsbz_marketplace.model.TicketListHolder INSTANCE = new com.marketplace.fsbz_marketplace.model.TicketListHolder();

    private TicketListHolder() {
    }

    public static com.marketplace.fsbz_marketplace.model.TicketListHolder getInstance() {
        return INSTANCE;
    }

    public void setTicketList(ArrayList<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public ArrayList<Ticket> getTicketList() {return this.ticketList;}
}