package ru.andmosc.transferMoney.util;

import java.util.concurrent.atomic.AtomicInteger;

public class ErrorResponse {
    private static ErrorResponse instance;
    private final AtomicInteger id = new AtomicInteger(1);
    private String message;

    public static synchronized ErrorResponse getInstance() {
        if (instance == null) {
            instance = new ErrorResponse();
        }
        return instance;
    }

    public ErrorResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        id.getAndIncrement();
        this.message = message;
    }

    public int getId() {
        return id.get();
    }
}
