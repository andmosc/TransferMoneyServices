package ru.andmosc.transferMoney.exception;

public class ErrorVerificationCard extends RuntimeException {
    private final int id;
    public ErrorVerificationCard(String message,int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
