package ru.andmosc.transferMoney.exception;

import lombok.Getter;

@Getter
public class ErrorTransferCard extends RuntimeException {
    private final long id;
    public ErrorTransferCard(String message, long id) {
        super(message);
        this.id = id;
    }
}
