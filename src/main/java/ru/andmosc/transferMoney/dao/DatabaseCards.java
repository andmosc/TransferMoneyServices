package ru.andmosc.transferMoney.dao;

import lombok.Getter;
import ru.andmosc.transferMoney.domain.Card;

import java.util.Map;

@Getter
public class DatabaseCards {
    private final Map<String, Card> cards;
    private final String confirmationCode;
    private static DatabaseCards instance;

    public static synchronized DatabaseCards getInstance() {
        if (instance == null) {
            instance = new DatabaseCards();
        }
        return instance;
    }

    private DatabaseCards() {
        LoadCards loadCardsFromFile = new LoadCardsFromFile();
        cards = loadCardsFromFile.loadCards();

        ReceiveConfirmationCode receiveConfirmationCode = new ReceiveConfirmationCodeFromFile();
        confirmationCode = receiveConfirmationCode.receiveCode();
    }
}
