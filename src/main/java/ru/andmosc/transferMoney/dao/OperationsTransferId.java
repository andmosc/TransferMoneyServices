package ru.andmosc.transferMoney.dao;

import lombok.Getter;
import ru.andmosc.transferMoney.domain.Transfer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * генерация ID, хранения перевода и его ID
 */
@Getter
public class OperationsTransferId {
    private static OperationsTransferId instance;
    private final Map<Long, Transfer> mapTransferCards;

    public static synchronized OperationsTransferId getInstance() {
        if (instance == null) {
            instance = new OperationsTransferId();
        }
        return instance;
    }

    public OperationsTransferId() {
        mapTransferCards = new ConcurrentHashMap<>();
    }

    public void saveTransfer(Transfer moneyTransferBody, long idOperation) {
        mapTransferCards.put(idOperation, moneyTransferBody);
    }
}

