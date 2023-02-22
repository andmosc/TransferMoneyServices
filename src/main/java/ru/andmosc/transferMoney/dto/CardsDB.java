package ru.andmosc.transferMoney.dto;

import ru.andmosc.transferMoney.models.Card;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class CardsDB {
    private final static String PATH = "src/main/resources/DBCards.properties";
    private final Map<String, Card> listCards;
    private final String confirmationCode;
    private static CardsDB instance;

    public static synchronized CardsDB getInstance() {
        if (instance == null) {
            instance = new CardsDB();
        }
        return instance;
    }

    private CardsDB() {
        listCards = new ConcurrentHashMap<>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addCards(properties);
        confirmationCode = properties.getProperty("CODE");
    }

    private void addCards(Properties properties) {
        for (int i = 1; i <= Integer.parseInt(properties.getProperty("NUMBER_REG_CARDHOLDER")); i++) {

            listCards.put(properties.getProperty("CARD_NUMBER_" + i), new Card(
                    properties.getProperty("CARD_NUMBER_" + i),
                    properties.getProperty("CARD_VALID_TILL_" + i),
                    properties.getProperty("CARD_CVV_" + i),
                    Long.parseLong(properties.getProperty("CARD_ACCOUNT_" + i)),
                    properties.getProperty("CURRENCY_" + i)
            ));
        }
    }

    public Map<String, Card> getListCards() {
        return listCards;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }
}
