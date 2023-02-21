package ru.andmosc.transferMoney.util;

import ru.andmosc.transferMoney.models.MoneyTransfer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class OperationsTransfer {
    private final static String PATH = "src/main/resources/OperationID.properties";
    private static OperationsTransfer instance;
    private final AtomicInteger id;
    private final Map<String, MoneyTransfer> mapTransferCards;
    private final Properties properties;
    private String lastID;


    public static synchronized OperationsTransfer getInstance() {
        if (instance == null) {
            return new OperationsTransfer();
        }
        return instance;
    }

    private OperationsTransfer() {
        properties = new Properties();
        mapTransferCards = new ConcurrentHashMap<>();
        id = new AtomicInteger();
    }

    /**
     * Получение ID , сохранеие в map
     * @param moneyTransferBody
     * @return
     */
    public synchronized String verificationCompleted(MoneyTransfer moneyTransferBody) {

        try {
            FileInputStream in = new FileInputStream(PATH);
            properties.load(in);
            in.close();
            id.set(Integer.parseInt(properties.getProperty("ID_OPERATION")));
            lastID = String.valueOf(id.get());

            mapTransferCards.put(String.valueOf(id.get()), moneyTransferBody);

            FileOutputStream out = new FileOutputStream(PATH);
            properties.setProperty("ID_OPERATION", String.valueOf(id.getAndIncrement()));
            properties.store(out, null);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(id.get());
    }

    public String getId() {
        return lastID;
    }

    public Map<String, MoneyTransfer> getMapTransferCards() {
        return mapTransferCards;
    }
}

