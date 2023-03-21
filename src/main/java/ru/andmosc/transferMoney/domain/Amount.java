package ru.andmosc.transferMoney.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Amount {
    private final Long value;
    private final String currency;
}
