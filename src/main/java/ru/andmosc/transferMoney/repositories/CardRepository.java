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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Repository
@Slf4j
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
        String id = operationsTransfer.verificationCompleted(moneyTransferBody);
        log.info("Номер транзакции перевода: {}", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    public boolean confirmOperation(ConfirmOperation confirmOperationBody) {
        confirmOperationBody.setOperationId(operationsTransfer.getId());
        return codeConfirm().equals(confirmOperationBody.getCode());
    }

    public ResponseEntity<?> confirmOperationCompleted(ConfirmOperation confirmOperationBody) {
        return new ResponseEntity<>(confirmOperationBody.getCode(), HttpStatus.OK);
    }

    private String codeConfirm() {
        try {
            properties.load(new FileInputStream(PATH));
            return properties.getProperty("CODE");
        } catch (IOException e) {
            log.error("Ошибка загрузки файла: {}", PATH);
            throw new RuntimeException(e);
        }
    }

    public String operationByCard(ConfirmOperation confirmOperationBody) {
        StringBuilder operation = new StringBuilder();
        Map<String, MoneyTransfer> mapTransferCard = operationsTransfer.getMapTransferCards();
        MoneyTransfer moneyTransfer = mapTransferCard.get(confirmOperationBody.getOperationId());
        operation.append("Списание с карты: ")
                .append(moneyTransfer.getCardFromNumber())
                .append(", Карта зачисления: ")
                .append(moneyTransfer.getCardToNumber())
                .append(", Сумма: ")
                .append(moneyTransfer.getAmount().getValue())
                .append(" ")
                .append(moneyTransfer.getAmount().getCurrency())
                .append(", номер транзакции: ")
                .append(confirmOperationBody.getOperationId());
        return new String(operation);
    }
}
