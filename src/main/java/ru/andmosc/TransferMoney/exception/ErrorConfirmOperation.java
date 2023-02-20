package ru.andmosc.TransferMoney.exception;

public class ErrorConfirmOperation extends RuntimeException {
    public ErrorConfirmOperation(String message) {
        super(message);
    }
}
