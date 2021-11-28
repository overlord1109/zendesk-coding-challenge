package com.zendesk.zccstudents1109.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Date;

@JsonRootName("ticket")
public class Ticket {

    private long id;
    private String description;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("status")
    private String status;
    @JsonProperty("requester_id")
    private String requesterId;
    @JsonProperty("submitter_id")
    private String submitterId;
    @JsonProperty("assignee_id")
    private String assigneeId;


    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }

    @Override
    public String toString() {
        return "Ticket #" + id + ", requested by: " + requesterId + "at " + createdAt + "\nSubmitted by: " + submitterId + "\nAssigned to: " + assigneeId + "\nTicket status: " + status
                + "\nDescription:\n**************************************\n" + description + "\n**************************************\n"
                ;
    }
}
