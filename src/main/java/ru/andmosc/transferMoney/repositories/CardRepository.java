package ru.andmosc.transferMoney.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.andmosc.transferMoney.dto.CardsDB;
import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.models.Card;
import ru.andmosc.transferMoney.models.MoneyTransfer;
import ru.andmosc.transferMoney.util.OperationsTransfer;

import java.util.Collections;
import java.util.Map;

@Repository
@Slf4j
public class CardRepository {
    private final Map<String, Card> listCards;
    private final OperationsTransfer operationsTransfer;
    private Card card;
    private final CardsDB cardsDB;

    public CardRepository() {
        cardsDB = CardsDB.getInstance();
        operationsTransfer = OperationsTransfer.getInstance();
        listCards = cardsDB.getListCards();
    }

    public boolean verificationNumberCard(MoneyTransfer moneyTransferBody) {
        if (listCards.containsKey(moneyTransferBody.getCardFromNumber())) {
            card = listCards.get(moneyTransferBody.getCardFromNumber());
            return true;
        }
        return false;
    }

    public boolean verificationCardCVV(MoneyTransfer moneyTransferBody) {
        return card.getCardCVV().equals(moneyTransferBody.getCardFromCVV());
    }

    public boolean verificationAccount(MoneyTransfer moneyTransferBody) {
        return card.getAccount() >= moneyTransferBody.getAmount().getValue();
    }

    public boolean verificationValidTill(MoneyTransfer moneyTransferBody) {
        return card.getCardValidTill().equals(moneyTransferBody.getCardFromValidTill());
    }

    public ResponseEntity<?> saveVerification(MoneyTransfer moneyTransferBody,int idOperation) {
        operationsTransfer.saveVerification(moneyTransferBody,idOperation);
        return new ResponseEntity<>(Collections.singletonMap("operationId", idOperation), HttpStatus.OK);
    }

    public boolean confirmOperation(ConfirmOperation confirmOperationBody) {
        return cardsDB.getConfirmationCode().equals(confirmOperationBody.getCode());
    }

    public ResponseEntity<?> confirmOperationCompleted(ConfirmOperation confirmOperationBody) {
        return new ResponseEntity<>(Collections.singletonMap("operationId", confirmOperationBody.getOperationId()), HttpStatus.OK);
    }

    public String operationByCard(ConfirmOperation confirmOperationBody) {
        StringBuilder operation = new StringBuilder();
        Map<String, MoneyTransfer> mapTransferCard = operationsTransfer.getMapTransferCards();
        MoneyTransfer moneyTransfer = mapTransferCard.get(confirmOperationBody.getOperationId());
        operation.append("???????????????? ?? ??????????: ")
                .append(moneyTransfer.getCardFromNumber())
                .append(", ?????????? ????????????????????: ")
                .append(moneyTransfer.getCardToNumber())
                .append(", ??????????: ")
                .append(moneyTransfer.getAmount().getValue())
                .append(" ")
                .append(moneyTransfer.getAmount().getValue())
                .append(", id ????????????????: ")
                .append(confirmOperationBody.getOperationId());
        return new String(operation);
    }

    public int getOperationID() {
        return operationsTransfer.getIdOperation();
    }
}
