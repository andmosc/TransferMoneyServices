package ru.andmosc.TransferMoney.exception;

public class ErrorVerificationCard extends RuntimeException {
    public ErrorVerificationCard(String message) {
        super(message);
    }
}
