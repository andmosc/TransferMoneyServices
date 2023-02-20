package ru.andmosc.TransferMoney.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.andmosc.TransferMoney.dto.ConfirmOperation;
import ru.andmosc.TransferMoney.exception.ErrorConfirmOperation;
import ru.andmosc.TransferMoney.exception.ErrorVerificationCard;
import ru.andmosc.TransferMoney.models.MoneyTransfer;
import ru.andmosc.TransferMoney.services.TransferServices;
import ru.andmosc.TransferMoney.util.ErrorResponse;

@RestController
public class TransferController {
    private final TransferServices transferServices;

    @Autowired
    public TransferController(TransferServices transferServices) {
        this.transferServices = transferServices;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> verificationCard(@RequestBody  MoneyTransfer moneyTransferBody) {
        //TODO поступил запрос от клиента на перевод
        return transferServices.verificationCard(moneyTransferBody);
    }
    @PostMapping("/confirmOperation")
    public ResponseEntity<?> confirmOperation(@RequestBody ConfirmOperation confirmOperationBody) {
        //TODO отправка кода подтверждения
        return transferServices.confirmOperation(confirmOperationBody);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> errorVerificationCard(ErrorVerificationCard e) {
        ErrorResponse response = ErrorResponse.getInstance();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> errorConfirmOperation(ErrorConfirmOperation e) {
        ErrorResponse response = ErrorResponse.getInstance();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}