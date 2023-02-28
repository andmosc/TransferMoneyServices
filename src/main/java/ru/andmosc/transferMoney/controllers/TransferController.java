package ru.andmosc.transferMoney.controllers;

import lombok.extern.slf4j.Slf4j;
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
import ru.andmosc.transferMoney.services.TransferServicesImpl;
import ru.andmosc.transferMoney.util.ErrorResponse;

@RestController
@Slf4j
public class TransferController {
    private final TransferServicesImpl transferServicesImpl;

    @Autowired
    public TransferController(TransferServicesImpl transferServicesImpl) {
        this.transferServicesImpl = transferServicesImpl;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> verificationCard(@RequestBody MoneyTransfer moneyTransferBody) {
        log.info("POST: /transfer -> {}", moneyTransferBody);
        return transferServicesImpl.verificationCard(moneyTransferBody);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<?> confirmOperation(@RequestBody ConfirmOperation confirmOperationBody) {
        log.info("POST: /confirmOperation");
        return transferServicesImpl.confirmOperation(confirmOperationBody);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> errorVerificationCard(ErrorVerificationCard e) {
        ErrorResponse response = ErrorResponse.getInstance();
        response.setMessage(e.getMessage(), e.getId());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> errorConfirmOperation(ErrorConfirmOperation e) {
        ErrorResponse response = ErrorResponse.getInstance();
        response.setMessage(e.getMessage(), e.getId());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}