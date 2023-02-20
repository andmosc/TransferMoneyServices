package ru.andmosc.TransferMoney.util;

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
    private final Map<Integer, Long> mapOperaionID;
    private final Properties properties;


    public static synchronized OperationsTransfer getInstance() {
        if (instance == null) {
            return new OperationsTransfer();
        }
        return instance;
    }

    private OperationsTransfer() {
        properties = new Properties();
        mapOperaionID = new ConcurrentHashMap<>();
        id = new AtomicInteger(1);
    }

    /**
     * номер ID операции и ее сумма
     *
     * @param count сумма
     * @return
     */
    public synchronized Map<Integer, Long> operationCompleted(Long count) {

        try {
            FileInputStream in = new FileInputStream(PATH);
            properties.load(in);
            in.close();
            id.set(Integer.parseInt(properties.getProperty("ID_OPERATION")));
            FileOutputStream out = new FileOutputStream(PATH);
            mapOperaionID.put(id.getAndIncrement(), count);
            properties.setProperty("ID_OPERATION", String.valueOf(id.get()));
            properties.store(out, null);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mapOperaionID;
    }

    public int getId() {
        return id.get();
    }
}

