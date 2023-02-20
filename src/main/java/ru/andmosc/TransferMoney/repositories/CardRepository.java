package ru.andmosc.TransferMoney.repositories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.andmosc.TransferMoney.dto.CardsDB;
import ru.andmosc.TransferMoney.dto.ConfirmOperation;
import ru.andmosc.TransferMoney.models.Card;
import ru.andmosc.TransferMoney.models.MoneyTransfer;
import ru.andmosc.TransferMoney.util.OperationsTransfer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Repository
public class CardRepository {
    private final static String PATH = "src/main/resources/DBCards.properties";
    private final Map<String, Card> listCards;
    private final OperationsTransfer operationsTransfer;
    private Card card;
    private final Properties properties;

    public CardRepository() {
        CardsDB cardsDB = CardsDB.getInstance();
        operationsTransfer = OperationsTransfer.getInstance();
        listCards = cardsDB.getListCards();
        properties = new Properties();
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

    public ResponseEntity<?> verificationCompleted(MoneyTransfer moneyTransferBody) {
        return new ResponseEntity<>(operationsTransfer
                .operationCompleted(moneyTransferBody.getAmount().getValue()), HttpStatus.OK);
    }

    public boolean confirmOperation(ConfirmOperation confirmOperationBody) {
        confirmOperationBody.setOperationId(String.valueOf(operationsTransfer.getId()));
        return codeConfirm().equals(confirmOperationBody.getCode());
    }

    public ResponseEntity<?> confirmOperationCompleted(ConfirmOperation confirmOperationBody) {
        //TODO код подтвержден
        return new ResponseEntity<>(confirmOperationBody.getCode(), HttpStatus.OK);
    }

    private String codeConfirm() {
        try {
            properties.load(new FileInputStream(PATH));
            return properties.getProperty("CODE");
        } catch (IOException e) {
            //TODO ошибка чтения файла
            throw new RuntimeException(e);
        }
    }
}
