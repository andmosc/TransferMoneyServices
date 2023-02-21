package ru.andmosc.transferMoney.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.exception.ErrorConfirmOperation;
import ru.andmosc.transferMoney.exception.ErrorVerificationCard;
import ru.andmosc.transferMoney.models.MoneyTransfer;
import ru.andmosc.transferMoney.services.TransferServices;
import ru.andmosc.transferMoney.util.ErrorResponse;

@RestController

public class TransferController {
    private final TransferServices transferServices;
    private static final Logger log = LoggerFactory.getLogger(TransferServices.class);
    @Autowired
    public TransferController(TransferServices transferServices) {
        this.transferServices = transferServices;
    }
    @PostMapping("/transfer")
    public ResponseEntity<?> verificationCard(@RequestBody  MoneyTransfer moneyTransferBody) {
        log.info("POST: /transfer -> {}",moneyTransferBody);
        return transferServices.verificationCard(moneyTransferBody);
    }
    @PostMapping("/confirmOperation")
    public ResponseEntity<?> confirmOperation(@RequestBody ConfirmOperation confirmOperationBody) {
        log.info("POST: /confirmOperation");
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