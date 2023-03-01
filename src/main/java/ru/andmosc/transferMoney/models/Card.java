package ru.andmosc.transferMoney.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Card {
    private final String cardNumber;
    private final String cardValidTill;
    private final String cardCVV;
    private final Long  account;
    private final String currency;
}
