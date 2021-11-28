package com.zendesk.zccstudents1109.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Response;
import com.zendesk.zccstudents1109.exception.ApiException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZendeskApiService {

    private final ZendeskApiClient client;
    private final ObjectMapper mapper;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ZendeskApiService(ZendeskApiClient client) {
        this.client = client;
        this.mapper = new ObjectMapper();
        configureMapper();
    }

    private void configureMapper() {
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public boolean isUserAuthenticated() throws IOException, ApiException {
       Response response = client.isAuthenticated();
       if(response.code() == 403 || response.code() == 401) {
           return false;
       } else if(response.isSuccessful()) {
           return true;
       } else {
           throw new ApiException("Unexpected error while authenticating user");
       }
    }

    public Ticket getTicketById(long id) throws IOException, ApiException {
        Response response = client.getTicketById(id);

        logger.log(Level.FINE, "Fetched response for ticket with id " + id);
        if (response.isSuccessful()) {
            return mapper.reader()
                    .with(DeserializationFeature.UNWRAP_ROOT_VALUE)
                    .readValue(response.body().string(), Ticket.class);
        } else if (response.code() == 404) {
            logger.log(Level.FINE, "Ticket with id " + id + " not found");
            throw new ApiException("Ticket with id " + id + " not found");
        } else {
            logger.log(Level.FINE, "Got error code " + response.code() + " while requesting data for ticket id " + id);
            throw new ApiException("Exception while fetching ticket with id " + id);
        }
    }

    public PagedTicketResponse getPagedTickets() throws IOException {
        Response response = client.getPagedTickets(25);
        return mapper.reader().readValue(response.body().string(), PagedTicketResponse.class);
    }

    public PagedTicketResponse getNextPage(String nextLink) throws IOException {
        Response response = client.getNextPage(nextLink);
        return mapper.reader().readValue(response.body().string(), PagedTicketResponse.class);
    }

}
