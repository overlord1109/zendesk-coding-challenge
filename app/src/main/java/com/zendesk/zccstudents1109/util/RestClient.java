package com.zendesk.zccstudents1109.util;

import com.squareup.okhttp.OkHttpClient;

public class RestClient {

    private final OkHttpClient client;

    public RestClient() {
        this.client = new OkHttpClient();
    }

}
