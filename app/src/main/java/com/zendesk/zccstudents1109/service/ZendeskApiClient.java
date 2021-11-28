package com.zendesk.zccstudents1109.service;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Client to call Zendesk Ticket and User APIs
**/
public class ZendeskApiClient {

    private final OkHttpClient client;
    private final String email;
    private final String apiToken;
    private final String baseUrl;

    private final String GET_TICKET = "/api/v2/tickets/";
    private final String SHOW_SELF = "/api/v2/users/me/";

    public ZendeskApiClient(OkHttpClient client, String email, String apiToken, String baseUrl) {
        this.client = client;
        this.email = email;
        this.apiToken = apiToken;
        this.baseUrl = baseUrl;
    }

    private Headers getHeaders() {
        byte[] creds = (email + "/token:" + apiToken).getBytes(StandardCharsets.UTF_8);
        String encodedCreds = new String(Base64.getEncoder().encode(creds));

        return new Headers.Builder()
                .add("Authorization", "Basic " + encodedCreds)
                .add("Accept", "application/json")
                .build();
    }

    private Response callGetApi(String url) throws IOException {
        Request request = new Request.Builder()
                .headers(getHeaders())
                .url(url)
                .get()
                .build();

        return client.newCall(request).execute();
    }

    public Response isAuthenticated() throws IOException {
        return callGetApi(baseUrl + SHOW_SELF);
    }

    public Response getTicketById(long id) throws IOException {
        return callGetApi(baseUrl + GET_TICKET + id);
    }

    public Response getPagedTickets(int pageSize) throws IOException {
        return callGetApi(baseUrl + GET_TICKET + "?page[size]=" + pageSize);
    }

    public Response getNextPage(String nextLink) throws IOException {
        return callGetApi(nextLink);
    }
}
