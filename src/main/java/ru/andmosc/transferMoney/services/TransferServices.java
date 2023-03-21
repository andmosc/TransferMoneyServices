package ru.andmosc.transferMoney.services;

import ru.andmosc.transferMoney.domain.ConfirmOperation;
import ru.andmosc.transferMoney.domain.Transfer;

public interface TransferServices {
    long transferMoney(Transfer moneyTransferBody);
    long confirmOperation(ConfirmOperation confirmOperationBody);
}
