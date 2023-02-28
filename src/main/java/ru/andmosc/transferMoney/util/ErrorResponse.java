package ru.andmosc.transferMoney.util;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    public void setMessage(String message, int id) {
        this.message = message;
        this.id = id;
    }

}
