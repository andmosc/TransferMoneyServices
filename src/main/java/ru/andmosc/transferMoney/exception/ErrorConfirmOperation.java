package ru.andmosc.transferMoney.exception;

import lombok.Getter;

@Getter
public class ErrorConfirmOperation extends RuntimeException {
    private final long id;
    public ErrorConfirmOperation(String message,long id) {
        super(message);
        this.id = id;
    }
}
