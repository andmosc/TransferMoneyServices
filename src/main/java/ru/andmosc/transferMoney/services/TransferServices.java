package ru.andmosc.transferMoney.services;

import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.dto.Transfer;

public interface TransferServices {
    long transferMoney(Transfer moneyTransferBody);
    long confirmOperation(ConfirmOperation confirmOperationBody);
}
