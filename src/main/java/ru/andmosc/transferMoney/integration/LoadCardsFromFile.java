package ru.andmosc.transferMoney.integration;

import ru.andmosc.transferMoney.domain.Card;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class LoadCardsFromFile implements LoadCards {
    private final static String PATH = "src/main/resources/DBCards.properties";
    private final Map<String, Card> cards = new ConcurrentHashMap<>();
    private final Properties properties = new Properties();

    @Override
    public Map<String, Card> loadCards() {
        try {
            properties.load(new FileInputStream(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 1; i <= Integer.parseInt(properties.getProperty("NUMBER_REG_CARDHOLDER")); i++) {

            cards.put(properties.getProperty("CARD_NUMBER_" + i), new Card(
                    properties.getProperty("CARD_NUMBER_" + i),
                    properties.getProperty("CARD_VALID_TILL_" + i),
                    properties.getProperty("CARD_CVV_" + i),
                    Long.parseLong(properties.getProperty("CARD_ACCOUNT_" + i)),
                    properties.getProperty("CURRENCY_" + i)
            ));
        }
        return cards;
    }
}
