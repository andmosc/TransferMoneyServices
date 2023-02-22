package ru.andmosc.transferMoney.exception;

public class ErrorConfirmOperation extends RuntimeException {
    private final int id;
    public ErrorConfirmOperation(String message,int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
