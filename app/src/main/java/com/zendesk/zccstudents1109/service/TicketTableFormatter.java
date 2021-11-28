package com.zendesk.zccstudents1109.service;


/**
 * Formats and prints a page of tickets as a table
 **/
public class TicketTableFormatter {

    public void printTable(PagedTicketResponse response) {
        String leftAlignFormat = "| %-4s | %-15s | %-8s | %-28s |%n";

        System.out.format("+------+-----------------+--------+--------------------------------+%n");
        System.out.format("| ID   | Requester       | Status | Created at                     |%n");
        System.out.format("+------+-----------------+--------+--------------------------------+%n");
        for (Ticket ticket : response.getTickets()) {
            System.out.format(leftAlignFormat, ticket.getId(), ticket.getRequesterId(), ticket.getStatus(), ticket.getCreatedAt());
        }
        System.out.format("+------+-----------------+--------+--------------------------------+%n");
    }
}
