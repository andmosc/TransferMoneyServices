package ru.andmosc.transferMoney.services;

import org.springframework.http.ResponseEntity;
import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.models.MoneyTransfer;

public interface TransferServices {
    ResponseEntity<?> verificationCard(MoneyTransfer moneyTransferBody);
    ResponseEntity<?> confirmOperation(ConfirmOperation confirmOperationBody);
}
