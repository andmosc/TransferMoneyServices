package ru.andmosc.transferMoney.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
    private final String cardNumber;
    private final String cardValidTill;
    private final String cardCVV;
    private final Long  account;
    private final String currency;
}
