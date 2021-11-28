package com.zendesk.zccstudents1109.cli;

import com.zendesk.zccstudents1109.exception.ApiException;
import com.zendesk.zccstudents1109.service.PagedTicketResponse;
import com.zendesk.zccstudents1109.service.TicketTableFormatter;
import com.zendesk.zccstudents1109.service.ZendeskApiService;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class CommandLineInterface {

    private final Scanner in;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
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
                    String idString;
                    boolean matches;
                    do {
                        System.out.println("Enter ticket id:");
                        idString = in.nextLine();
                        matches = idString.matches("\\d+");
                        if(!matches) {
                            System.out.println("Please enter a valid number");
                        }
                    } while (!matches);
                    long id = Long.parseLong(idString);
                    System.out.println(service.getTicketById(id));
                } else if ("2".equals(s)) {
                    PagedTicketResponse pagedTickets = service.getPagedTickets();
                    formatter.printTable(pagedTickets);
                    while (pagedTickets.hasMore()) {
                        System.out.println("Press any key to view more tickets");
                        in.nextLine();
                        pagedTickets = service.getNextPage(pagedTickets.getNextLink());
                        formatter.printTable(pagedTickets);
                    }
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
}
