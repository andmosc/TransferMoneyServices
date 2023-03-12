package ru.andmosc.transferMoney.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.exception.ErrorConfirmOperation;
import ru.andmosc.transferMoney.exception.ErrorVerificationCard;
import ru.andmosc.transferMoney.models.Amount;
import ru.andmosc.transferMoney.models.MoneyTransfer;
import ru.andmosc.transferMoney.repositories.CardRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestServices {

    private final CardRepository cardRepository = Mockito.mock(CardRepository.class);
    private MoneyTransfer moneyTransferBody;
    private TransferServicesImpl transferServices;

    private static final int ID = 1;

    @BeforeEach
    void setUp() {
        moneyTransferBody = new MoneyTransfer("cardFromNumber", "cardToNumber",
                "cardFromCVV", "cardFromValidTill", new Amount(0L, "currency"));
        transferServices = new TransferServicesImpl(cardRepository);
    }

    @Test
    public void testVerificationCard_ErrorNumberCard() {
        final String event = "Ошибка ввода номера карты: cardFromNumber";

        Mockito.when(cardRepository.verificationNumberCard(moneyTransferBody)).thenReturn(false);

        Exception actual = assertThrows(ErrorVerificationCard.class, () -> transferServices.verificationCard(moneyTransferBody));

        assertEquals(event, actual.getMessage());
        Mockito.verify(cardRepository).verificationNumberCard(moneyTransferBody);
    }
    @Test
    public void testVerificationCard_ErrorCardCVV() {
        final String event = "CVV указан не верно: cardFromCVV";
        Mockito.when(cardRepository.verificationNumberCard(moneyTransferBody)).thenReturn(true);
        Mockito.when(cardRepository.verificationCardCVV(moneyTransferBody)).thenReturn(false);

        Exception actual = assertThrows(ErrorVerificationCard.class, () -> transferServices.verificationCard(moneyTransferBody));

        assertEquals(event, actual.getMessage());
        Mockito.verify(cardRepository).verificationCardCVV(moneyTransferBody);
    }
    @Test
    public void testVerificationCard_ErrorValidTill() {
        final String event = "Ошибка ввода даты карты: cardFromValidTill";
        Mockito.when(cardRepository.verificationNumberCard(moneyTransferBody)).thenReturn(true);
        Mockito.when(cardRepository.verificationCardCVV(moneyTransferBody)).thenReturn(true);
        Mockito.when(cardRepository.verificationValidTill(moneyTransferBody)).thenReturn(false);

        Exception actual = assertThrows(ErrorVerificationCard.class, () -> transferServices.verificationCard(moneyTransferBody));

        assertEquals(event, actual.getMessage());
        Mockito.verify(cardRepository).verificationValidTill(moneyTransferBody);
    }
    @Test
    public void testVerificationCard_ErrorAccount() {
        final String event = "Недостаточно средств на карте: 0";
        Mockito.when(cardRepository.verificationNumberCard(moneyTransferBody)).thenReturn(true);
        Mockito.when(cardRepository.verificationCardCVV(moneyTransferBody)).thenReturn(true);
        Mockito.when(cardRepository.verificationValidTill(moneyTransferBody)).thenReturn(true);
        Mockito.when(cardRepository.verificationAccount(moneyTransferBody)).thenReturn(false);

        Exception actual = assertThrows(ErrorVerificationCard.class, () -> transferServices.verificationCard(moneyTransferBody));

        assertEquals(event, actual.getMessage());
        Mockito.verify(cardRepository).verificationAccount(moneyTransferBody);
    }

    @Test
    public void testConfirmOperation_ErrorConfirmOperation() {
        final String event = "Код подтверждения не принят: 1111";
        ConfirmOperation confirmOperation = new ConfirmOperation("1","1111");
        Mockito.when(cardRepository.confirmOperation(confirmOperation)).thenReturn(false);


        Exception actual = assertThrows(ErrorConfirmOperation.class, () -> transferServices.confirmOperation(confirmOperation));

        assertEquals(event, actual.getMessage());
        Mockito.verify(cardRepository).confirmOperation(confirmOperation);
    }
}
