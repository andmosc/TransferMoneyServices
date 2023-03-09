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


    public static synchronized OperationsTransfer getInstance() {
        if (instance == null) {
            instance = new OperationsTransfer();
        }
        return instance;
    }

    private OperationsTransfer() {
        properties = new Properties();
        mapTransferCards = new ConcurrentHashMap<>();
        id = new AtomicInteger();
    }

    public void saveVerification(MoneyTransfer moneyTransferBody,int idOperation) {
        mapTransferCards.put(String.valueOf(idOperation), moneyTransferBody);
    }

    public synchronized int getIdOperation() {
        try {
            FileInputStream in = new FileInputStream(PATH);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Integer.parseInt(properties.getProperty("ID_OPERATION"));
    }

    public synchronized int saveNewIdOperation(int idOperation) {
        id.set(idOperation);
        try {
            FileOutputStream out = new FileOutputStream(PATH);
            properties.setProperty("ID_OPERATION", String.valueOf(id.incrementAndGet()));
            properties.store(out, null);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return id.get();
    }

    public Map<String, MoneyTransfer> getMapTransferCards() {
        return mapTransferCards;
    }
}

