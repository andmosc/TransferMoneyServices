package ru.andmosc.transferMoney.models;

public class Card {
    private final String cardNumber;
    private final String cardValidTill;
    private final String cardCVV;
    private final Long  account;
    private final String currency;

    public Card(String cardNumber, String cardValidTill, String cardCVV, Long account, String currency) {
        this.cardNumber = cardNumber;
        this.cardValidTill = cardValidTill;
        this.cardCVV = cardCVV;
        this.account = account;
        this.currency = currency;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardValidTill() {
        return cardValidTill;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public Long getAccount() {
        return account;
    }

    public String getCurrency() {
        return currency;
    }
}
