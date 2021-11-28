package com.zendesk.zccstudents1109.ticket;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ticket")
public class Ticket {

    private long id;
    private String description;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
