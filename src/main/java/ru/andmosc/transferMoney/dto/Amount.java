package ru.andmosc.transferMoney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Amount {
    private final Long value;
    private final String currency;
}
