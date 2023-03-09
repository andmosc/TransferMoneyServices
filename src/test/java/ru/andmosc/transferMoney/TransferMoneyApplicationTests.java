package ru.andmosc.transferMoney;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.models.Amount;
import ru.andmosc.transferMoney.models.MoneyTransfer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferMoneyApplicationTests {
    private static final Integer PORT = 5500;
    private static final String PATH = "src/main/resources/DBCards.properties";
    @Autowired
    private TestRestTemplate restTemplate;
    private static Properties properties;

    private static MoneyTransfer request;
    private static Amount amount;
    @Container
    private static final GenericContainer<?> myApp = new GenericContainer<>("myapp:1.0")
            .withExposedPorts(PORT);

    @BeforeAll
    public static void init() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        amount = new Amount(100L, "RUR");
    }

    @Test
    void testTransferReturnCorrectOperationId() {
        request = new MoneyTransfer(
                properties.getProperty("CARD_NUMBER_1"),
                properties.getProperty("CARD_NUMBER_2"),
                properties.getProperty("CARD_CVV_1"),
                properties.getProperty("CARD_VALID_TILL_1"),
                amount
        );

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + myApp.getMappedPort(PORT) + "/transfer", request, String.class
        );
        Assertions.assertEquals("{\"operationId\":\"1\"}", response.getBody());
    }

    @Test
    void testConfirmOperationReturnCorrectOperationId() {
        ConfirmOperation confirmOperationBody = new ConfirmOperation("1", "0000");
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + myApp.getMappedPort(PORT) + "/confirmOperation", confirmOperationBody, String.class
        );
        Assertions.assertEquals("{\"operationId\":\"1\"}", response.getBody());
    }

    @Test
    void testTransferWhenInvalidNumberCard() {
        request = new MoneyTransfer(
                "1111111111111110",
                properties.getProperty("CARD_NUMBER_2"),
                properties.getProperty("CARD_CVV_1"),
                properties.getProperty("CARD_VALID_TILL_1"),
                amount
        );

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + myApp.getMappedPort(PORT) + "/transfer", request, String.class
        );
        Assertions.assertEquals(
                "{\"message\":\"Ошибка ввода номера карты: 1111111111111110\",\"id\":1}"
                , response.getBody());
    }
    @Test
    void testTransferWhenInvalidCardValidCard() {
        request = new MoneyTransfer(
                properties.getProperty("CARD_NUMBER_1"),
                properties.getProperty("CARD_NUMBER_2"),
                "01/25",
                properties.getProperty("CARD_VALID_TILL_1"),
                amount
        );

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + myApp.getMappedPort(PORT) + "/transfer", request, String.class
        );

        Assertions.assertEquals(
                "{\"message\":\"CVV указан не верно: 01/25\",\"id\":1}"
                , response.getBody());
    }

    @Test
    void testConfirmOperationWhenInvalidConfirmOperation() {
        ConfirmOperation confirmOperationBody = new ConfirmOperation("1", "0001");

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + myApp.getMappedPort(PORT) + "/confirmOperation", confirmOperationBody, String.class
        );

        Assertions.assertEquals(
                "{\"message\":\"Код подтверждения не принят: 0001\",\"id\":1}"
                , response.getBody());
    }
}
