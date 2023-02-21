package ru.andmosc.transferMoney.exception;

public class ErrorVerificationCard extends RuntimeException {
    public ErrorVerificationCard(String message) {
        super(message);
    }
}
