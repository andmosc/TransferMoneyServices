package ru.andmosc.transferMoney.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfirmOperation {
    private String operationId;
    private String code;
}
