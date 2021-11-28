package com.zendesk.zccstudents1109;

import com.squareup.okhttp.OkHttpClient;
import com.zendesk.zccstudents1109.cli.CommandLineInterface;
import com.zendesk.zccstudents1109.exception.ApiException;
import com.zendesk.zccstudents1109.service.ZendeskApiClient;
import com.zendesk.zccstudents1109.service.ZendeskApiService;
import com.zendesk.zccstudents1109.util.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        Properties props = new PropertiesLoader().loadProperties("application.properties");
        System.out.println("\n\nWelcome to Zendesk ticket viewer!\n");

        if(args.length != 2) {
            System.out.println("Please enter correct number of arguments.");
            return;
        }

        String email = args[0], token = args[1];
        ZendeskApiClient client = new ZendeskApiClient(new OkHttpClient(), email, token, props.getProperty("zendesk_base_url"));
        ZendeskApiService service = new ZendeskApiService(client);

        try {
            if(!service.isUserAuthenticated()) {
                System.out.println("User is not authenticated, please enter valid credentials");
                return;
            }
            CommandLineInterface cli = new CommandLineInterface(service);
            cli.run();
        } catch (IOException e) {
            System.out.println("Uh oh, something went wrong! Please try again later");
        } catch (ApiException e) {
            System.out.println(e.getMessage());
        }
    }
}
