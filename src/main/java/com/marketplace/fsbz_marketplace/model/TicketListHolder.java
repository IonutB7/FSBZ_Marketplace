package com.marketplace.fsbz_marketplace.model;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public final class TicketListHolder {

    private ObservableList<Ticket> ticketList;
    private final static com.marketplace.fsbz_marketplace.model.TicketListHolder INSTANCE = new com.marketplace.fsbz_marketplace.model.TicketListHolder();

    private TicketListHolder() {
    }

    public static com.marketplace.fsbz_marketplace.model.TicketListHolder getInstance() {
        return INSTANCE;
    }

    public void setTicketList(ObservableList<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public ObservableList<Ticket> getTicketList() {return this.ticketList;}
}