package ru.andmosc.transferMoney.dto;

public class ConfirmOperation {
    private final String operationId;
    private final String code;

    public ConfirmOperation(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getCode() {
        return code;
    }

}
