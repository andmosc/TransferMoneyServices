package ru.andmosc.transferMoney.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private static ErrorResponse instance;
    private String message;
    private long id;

    public static ErrorResponse getInstance() {
        if (instance == null) {
            instance = new ErrorResponse();
        }
        return instance;
    }

    public void setMessage(String message, long id) {
        this.message = message;
        this.id = id;
    }
}
