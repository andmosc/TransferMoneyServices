package ru.andmosc.transferMoney.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.andmosc.transferMoney.dao.DatabaseCards;
import ru.andmosc.transferMoney.dao.GenerationIdOperation;
import ru.andmosc.transferMoney.domain.Card;
import ru.andmosc.transferMoney.domain.ConfirmOperation;
import ru.andmosc.transferMoney.domain.Transfer;
import ru.andmosc.transferMoney.dao.OperationsTransferId;

import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class CardRepository {
    private final Map<String, Card> mapCards;
    private final OperationsTransferId operationsTransferId;
    private final DatabaseCards databaseCards;
    private final GenerationIdOperation generationIdOperation;

    public CardRepository() {
        databaseCards = DatabaseCards.getInstance();
        operationsTransferId = OperationsTransferId.getInstance();
        mapCards = databaseCards.getCards();
        generationIdOperation = new GenerationIdOperation();
    }

    public Optional<Card> verificationNumberCard(Transfer moneyTransferBody) {
        return Optional.ofNullable(mapCards.get(moneyTransferBody.getCardFromNumber()));
    }

    public boolean verificationCardCVV(Transfer moneyTransferBody, Card card) {
        return card.getCardCVV().equals(moneyTransferBody.getCardFromCVV());
    }

    public boolean verificationAccount(Transfer moneyTransferBody, Card card) {
        return card.getAccount() >= moneyTransferBody.getAmount().getValue();
    }

    public boolean verificationValidTill(Transfer moneyTransferBody, Card card) {
        return card.getCardValidTill().equals(moneyTransferBody.getCardFromValidTill());
    }

    public void saveTransferOperation(Transfer moneyTransferBody, long idOperation) {
        operationsTransferId.saveTransfer(moneyTransferBody,idOperation);
    }

    public boolean confirmOperation(ConfirmOperation confirmOperationBody) {
        return databaseCards.getConfirmationCode().equals(confirmOperationBody.getCode());
    }

    public long executeConfirmOperation(ConfirmOperation confirmOperationBody) {
        return Long.parseLong(confirmOperationBody.getOperationId());
    }

    public long getOperationID() {
        return generationIdOperation.getOperationId();
    }

    public String executeOperation(ConfirmOperation confirmOperationBody) {
        StringBuilder operation = new StringBuilder();

        Map<Long, Transfer> mapTransferCard = operationsTransferId.getMapTransferCards();
        Transfer moneyTransfer = mapTransferCard.get(Long.parseLong(confirmOperationBody.getOperationId()));
        operation.append("Списание с карты: ")
                .append(moneyTransfer.getCardFromNumber())
                .append(", Карта зачисления: ")
                .append(moneyTransfer.getCardToNumber())
                .append(", Сумма: ")
                .append(moneyTransfer.getAmount().getValue())
                .append(" ")
                .append(", id операции: ")
                .append(confirmOperationBody.getOperationId());
        return new String(operation);
    }
}
