package com.zendesk.zccstudents1109.service;

import com.squareup.okhttp.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ZendeskApiClientTest {

    private final String email = "some@email.com";
    private final String apiToken = "sometoken";
    private final String baseUrl = "https://someurl.com";

    @Test
    public void getsTicketById() throws IOException, URISyntaxException {
        final String responseBody = getTicketJson();
        ZendeskApiClient zendeskApiClient = new ZendeskApiClient(mockHttpClient(responseBody), email, apiToken, baseUrl);
        Response response = zendeskApiClient.getTicketById(1);
        assertNotNull(response);
        assertEquals(200, response.code());
    }

    private String getTicketJson() throws IOException, URISyntaxException {
        URL resourceUrl = getClass().getClassLoader().getResource("data/ticket.json");
        return new String(Files.readAllBytes(Paths.get(resourceUrl.toURI())));
    }

    private OkHttpClient mockHttpClient(String responseBody) throws IOException {
        OkHttpClient mockClient = Mockito.mock(OkHttpClient.class);
        Call mockCall = Mockito.mock(Call.class);
        Response response = new Response.Builder()
                .request(new Request.Builder().url(baseUrl).get().build())
                .body(ResponseBody.create(MediaType.parse("application/json"), responseBody))
                .protocol(Protocol.HTTP_2)
                .code(200)
                .build();
        Mockito.when(mockCall.execute()).thenReturn(response);
        Mockito.when(mockClient.newCall(Mockito.any())).thenReturn(mockCall);
        return mockClient;
    }
}
