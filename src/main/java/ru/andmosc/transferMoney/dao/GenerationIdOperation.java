package ru.andmosc.transferMoney.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GenerationIdOperation {
    private final static String PATH = "src/main/resources/OperationID.properties";
    private final AtomicLong id = new AtomicLong();
    private final Properties properties = new Properties();;

    public synchronized long getOperationId() {
        try {
            FileInputStream in = new FileInputStream(PATH);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long idOperation = Long.parseLong(String.valueOf(properties.getProperty("ID_OPERATION")));
        saveNewIdOperation(idOperation);
        return idOperation;
    }

    public long saveNewIdOperation(long idOperation) {
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
}
