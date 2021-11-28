package com.zendesk.zccstudents1109.exception;

public class ApiException extends Exception {
    private final String message;

    public ApiException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
