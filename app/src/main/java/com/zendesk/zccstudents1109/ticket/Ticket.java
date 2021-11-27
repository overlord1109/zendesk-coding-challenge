package com.zendesk.zccstudents1109.ticket;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ticket")
public class Ticket {

    private int id;

    public int getId() {
        return id;
    }
}
