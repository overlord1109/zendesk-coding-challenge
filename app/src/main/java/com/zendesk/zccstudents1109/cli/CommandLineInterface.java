package com.zendesk.zccstudents1109.cli;

import com.zendesk.zccstudents1109.exception.ApiException;
import com.zendesk.zccstudents1109.service.PagedTicketResponse;
import com.zendesk.zccstudents1109.service.TicketTableFormatter;
import com.zendesk.zccstudents1109.service.ZendeskApiService;

import java.io.IOException;
import java.util.Scanner;

/**
 * Looping CLI that takes input from user
 **/
public class CommandLineInterface {

    private final Scanner in;
    private final ZendeskApiService service;
    private final TicketTableFormatter formatter;

    public CommandLineInterface(ZendeskApiService service) {
        this.in = new Scanner(System.in);
        this.service = service;
        this.formatter = new TicketTableFormatter();
    }

    public void run() {
        boolean running = true;

        try {
            while (running) {
                System.out.println("Please enter:\n1 to view a ticket\n2 to view all tickets\nq to quit");
                String s = in.nextLine();
                if ("1".equals(s)) {
                    long id = getValidNumberFromUser();
                    System.out.println(service.getTicketById(id));
                } else if ("2".equals(s)) {
                    pageThroughAllTickets();
                } else if ("q".equals(s)) {
                    running = false;
                }
            }
        } catch (ApiException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Something went wrong, please try again later");
        }
    }

    /**
     * Validation to prevent NumberFormatException
     **/
    private long getValidNumberFromUser() {
        String idString;
        boolean isValidNumber;
        do {
            System.out.println("Enter ticket id:");
            idString = in.nextLine();
            isValidNumber = idString.matches("\\d+");
            if(!isValidNumber) {
                System.out.println("Please enter a valid number");
            }
        } while (!isValidNumber);
        return Long.parseLong(idString);
    }

    /**
     * Gets first page and subsequently follows `next` page links to display page-wise output
     **/
    private void pageThroughAllTickets() throws IOException {
        PagedTicketResponse pagedTickets = service.getPagedTickets();
        formatter.printTable(pagedTickets);
        while (pagedTickets.hasMore()) {
            System.out.println("Press any key to view more tickets");
            in.nextLine();
            pagedTickets = service.getNextPage(pagedTickets.getNextLink());
            formatter.printTable(pagedTickets);
        }
    }
}
