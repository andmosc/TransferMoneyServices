package ru.andmosc.transferMoney.domain;

import lombok.Getter;
import ru.andmosc.transferMoney.dto.Transfer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * хранения перевода и его ID
 */
@Getter
public class OperationsTransferId {
    private static OperationsTransferId instance;
    private final Map<Long, Transfer> mapTransferCards;

    public static OperationsTransferId getInstance() {
        if (instance == null) {
            instance = new OperationsTransferId();
        }
        return instance;
    }

    public OperationsTransferId() {
        mapTransferCards = new ConcurrentHashMap<>();
    }

    public void saveTransfer(long idOperation, Transfer moneyTransferBody) {
        mapTransferCards.put(idOperation, moneyTransferBody);
    }
}

