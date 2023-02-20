package ru.andmosc.TransferMoney.models;

public class Amount {
    private Long value;
    private String currency;

    public Amount() {
    }

    public Amount(Long value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Long getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

}
