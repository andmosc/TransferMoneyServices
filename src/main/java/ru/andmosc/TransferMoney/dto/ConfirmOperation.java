package ru.andmosc.TransferMoney.dto;

public class ConfirmOperation {
    private String operationId;
    private String code;

    public ConfirmOperation() {
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getCode() {
        return code;
    }

}
