package com.zendesk.zccstudents1109.ticket;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TicketClient {

    private final OkHttpClient client;
    private final String email;
    private final String apiToken;
    private final String baseUrl;
    private final ObjectMapper mapper;

    private final String GET_TICKET = "/api/v2/tickets/";

    public TicketClient(OkHttpClient client, String email, String apiToken, String baseUrl) {
        this.client = client;
        this.email = email;
        this.apiToken = apiToken;
        this.baseUrl = baseUrl;
        this.mapper = new ObjectMapper();
        configureMapper();
    }

    private void configureMapper() {
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
    }

    private Headers getHeaders() {
        byte[] creds = (email + "/token:" + apiToken).getBytes(StandardCharsets.UTF_8);
        String encodedCreds = new String(Base64.getEncoder().encode(creds));

        return new Headers.Builder()
                .add("Authorization", "Basic " + encodedCreds)
                .add("Accept", "application/json")
                .build();
    }

    public Ticket getTicketById(int id) throws IOException {
        Request request = new Request.Builder()
                .headers(getHeaders())
                .url(baseUrl + GET_TICKET + id)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        if(response.isSuccessful()) {
            return mapper.reader().readValue(response.body().string(), Ticket.class);
        } else if(response.code() >= 400) {
            //TODO: Handle error codes
            return null;
        }

        return null;
    }
}
