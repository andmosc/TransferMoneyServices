package ru.andmosc.transferMoney.util;

public class ErrorResponse {
    private static ErrorResponse instance;
    private String message;
    private int id;

    public static synchronized ErrorResponse getInstance() {
        if (instance == null) {
            instance = new ErrorResponse();
        }
        return instance;
    }

    public ErrorResponse() {
    }

    public void setMessage(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public Integer getId() {
        return id;
    }
}
