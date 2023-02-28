package ru.andmosc.transferMoney.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Amount {
    private final Long value;
    private final String currency;
}
