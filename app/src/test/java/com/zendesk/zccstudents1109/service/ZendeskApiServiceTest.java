package com.zendesk.zccstudents1109.service;

import com.squareup.okhttp.*;
import com.zendesk.zccstudents1109.exception.ApiException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ZendeskApiServiceTest {

    private final String baseUrl = "https://someurl.com";

    @Test
    public void getsTicketById() throws IOException, ApiException {
        ZendeskApiClient mockClient = Mockito.mock(ZendeskApiClient.class);
        Mockito.when(mockClient.getTicketById(Mockito.anyLong())).thenReturn(mockResponse(200, readResourceIntoString("data/ticket.json")));

        ZendeskApiService zendeskApiService = new ZendeskApiService(mockClient);
        Ticket ticket = zendeskApiService.getTicketById(1L);
        assertNotNull(ticket);
        assertEquals(1L, ticket.getId());
    }

    @Test
    public void throwsExceptionForMissingTicket() throws IOException {
        ZendeskApiClient mockClient = Mockito.mock(ZendeskApiClient.class);
        Mockito.when(mockClient.getTicketById(Mockito.anyLong())).thenReturn(mockResponse(404, "{\"error\":\"RecordNotFound\",\"description\":\"Not found\"}"));
        ZendeskApiService zendeskApiService = new ZendeskApiService(mockClient);
        assertThrows(ApiException.class, () -> zendeskApiService.getTicketById(2L));
    }

    @Test
    public void getsPagedTickets() throws IOException {
        ZendeskApiClient mockClient = Mockito.mock(ZendeskApiClient.class);
        Mockito.when(mockClient.getPagedTickets(Mockito.anyInt())).thenReturn(mockResponse(200, readResourceIntoString("data/paged_tickets.json")));
        ZendeskApiService service = new ZendeskApiService(mockClient);
        PagedTicketResponse pagedTicketResponse = service.getPagedTickets();
        assertTrue(pagedTicketResponse.hasMore());
        assertEquals(25, pagedTicketResponse.getTickets().size());
    }

    @Test
    public void authenticatesUser() throws IOException, ApiException {
        ZendeskApiClient mockClient = Mockito.mock(ZendeskApiClient.class);
        Mockito.when(mockClient.isAuthenticated()).thenReturn(mockResponse(200, ""));
        ZendeskApiService service = new ZendeskApiService(mockClient);
        assertTrue(service.isUserAuthenticated());
    }

    private Response mockResponse(int code, String responseBody) {
        return new Response.Builder()
                .request(new Request.Builder().url(baseUrl).get().build())
                .body(ResponseBody.create(MediaType.parse("application/json"), responseBody))
                .protocol(Protocol.HTTP_2)
                .code(code)
                .build();
    }

    private String readResourceIntoString(String resource) {
        URL resourceUrl = getClass().getClassLoader().getResource(resource);
        try {
            return new String(Files.readAllBytes(Paths.get(resourceUrl.toURI())));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
