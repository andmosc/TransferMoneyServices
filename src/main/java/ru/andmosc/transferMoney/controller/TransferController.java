package ru.andmosc.transferMoney.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.andmosc.transferMoney.domain.ConfirmOperation;
import ru.andmosc.transferMoney.domain.Transfer;
import ru.andmosc.transferMoney.domain.response.OperationId;
import ru.andmosc.transferMoney.services.TransferServicesImpl;

@RestController
@AllArgsConstructor
@Slf4j
public class TransferController {
    private final TransferServicesImpl transferServicesImpl;

    @PostMapping("/transfer")
    public ResponseEntity<OperationId> transferMoney(@RequestBody Transfer transferBody) {
        log.info("POST: /transfer -> {}", transferBody);
        long id = transferServicesImpl.transferMoney(transferBody);
        return ResponseEntity.ok(new OperationId(String.valueOf(id)));
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationId> confirmOperation(@RequestBody ConfirmOperation confirmOperationBody) {
        log.info("POST: /confirmOperation");
        long id = transferServicesImpl.confirmOperation(confirmOperationBody);
        return ResponseEntity.ok(new OperationId(String.valueOf(id)));
    }
}