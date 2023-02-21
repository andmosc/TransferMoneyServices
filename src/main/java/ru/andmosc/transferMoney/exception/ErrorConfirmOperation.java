package ru.andmosc.transferMoney.exception;

public class ErrorConfirmOperation extends RuntimeException {
    public ErrorConfirmOperation(String message) {
        super(message);
    }
}
