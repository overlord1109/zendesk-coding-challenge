package com.zendesk.zccstudents1109.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class PagedTicketResponse {

    @JsonProperty("tickets")
    private List<Ticket> tickets;
    private boolean hasMore;
    private String nextLink;

    @JsonProperty("meta")
    public void setHasMore(Map<String, String> meta) {
        this.hasMore = (Boolean.parseBoolean(meta.get("has_more")));
    }

    @JsonProperty("links")
    public void setNextLink(Map<String, String> link) {
        this.nextLink = link.get("next");
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public boolean hasMore() {
        return hasMore;
    }

    public String getNextLink() {
        return nextLink;
    }

}

