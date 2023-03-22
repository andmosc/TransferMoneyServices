package ru.andmosc.transferMoney.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.andmosc.transferMoney.domain.Amount;
import ru.andmosc.transferMoney.domain.Card;
import ru.andmosc.transferMoney.domain.ConfirmOperation;
import ru.andmosc.transferMoney.domain.Transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCardRepository {

    private static ConfirmOperation confirmOperation;
    private static Transfer moneyTransferBody;
    private static CardRepository repository;
    private static Card card;


    @BeforeAll
    public static void setUp() {
        repository = new CardRepository();
        moneyTransferBody = new Transfer("1111111111111111", "2222",
                "111", "01/24", new Amount(100L, "RUR"));
        confirmOperation = new ConfirmOperation("1", "0000");
        card = new Card("1111111111111111", "01/24", "111", 100000L, "RUR");
    }

    @Test
    void verificationNumberCard_ReturnTrue() {
        Card cardActual = repository.verificationNumberCard(moneyTransferBody).orElse(null);
        assertEquals(card, cardActual);
    }

    @Test
    void verificationCardCVV_ReturnTrue() {
        assertTrue(repository.verificationCardCVV(moneyTransferBody, card));
    }

    @Test
    void verificationAccount_ReturnTrue() {
        assertTrue(repository.verificationAccount(moneyTransferBody, card));
    }

    @Test
    void verificationValidTill_ReturnTrue() {
        assertTrue(repository.verificationValidTill(moneyTransferBody, card));
    }

    @Test
    void confirmOperation_ReturnTrue() {
        assertTrue(repository.confirmOperationCode(confirmOperation));
    }
}
