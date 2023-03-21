package ru.andmosc.transferMoney.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReceiveConfirmationCodeFromFile implements ReceiveConfirmationCode {
    private final static String PATH = "src/main/resources/DBCards.properties";
    private final Properties properties = new Properties();
    @Override
    public String receiveCode() {
        try {
            properties.load(new FileInputStream(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty("CODE");
    }
}
