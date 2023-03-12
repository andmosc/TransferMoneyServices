package ru.andmosc.transferMoney.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.models.Amount;
import ru.andmosc.transferMoney.models.MoneyTransfer;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCardRepository {

    private static ConfirmOperation confirmOperation;
    private static MoneyTransfer moneyTransferBody;
    private static CardRepository repository;


    @BeforeAll
    static void setUp() {
        repository = new CardRepository();
        moneyTransferBody = new MoneyTransfer("1111111111111111", "2222",
                "111", "01/24", new Amount(100L, "RUR"));
        confirmOperation = new ConfirmOperation("1", "0000");
    }

    @Test
    void testVerificationNumberCard_ReturnTrue() {
        assertTrue(repository.verificationNumberCard(moneyTransferBody));
    }

    @Test
    void testVerificationCardCVV_ReturnTrue() {
        assertTrue(repository.verificationCardCVV(moneyTransferBody));
    }

    @Test
    void testVerificationAccount_ReturnTrue() {
        assertTrue(repository.verificationAccount(moneyTransferBody));
    }

    @Test
    void testVerificationValidTill_ReturnTrue() {
        assertTrue(repository.verificationValidTill(moneyTransferBody));
    }

    @Test
    void testConfirmOperation_ReturnTrue() {
        assertTrue(repository.confirmOperation(confirmOperation));
    }
}
