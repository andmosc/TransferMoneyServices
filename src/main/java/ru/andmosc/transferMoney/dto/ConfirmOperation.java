package ru.andmosc.transferMoney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfirmOperation {
    private String operationId;
    private String code;
}
