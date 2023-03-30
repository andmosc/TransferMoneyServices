package ru.andmosc.transferMoney.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.andmosc.transferMoney.domain.Card;
import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.dto.Transfer;
import ru.andmosc.transferMoney.repository.CardRepository;
import ru.andmosc.transferMoney.util.ValidateCard;
import ru.andmosc.transferMoney.util.ValidateConfirmOperation;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestServices {
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private TransferServicesImpl transferServices;

    @Test
    public void verificationCard_shouldCallValidateCard() {
        final Transfer moneyTransferBody = Mockito.mock(Transfer.class);
        final ValidateCard validateCard = Mockito.mock(ValidateCard.class);
        final Card card = Mockito.mock(Card.class);
        final long id = 1L;
        Mockito.when(cardRepository.getOperationID()).thenReturn(1L);
        Mockito.when(cardRepository.verificationNumberCard(moneyTransferBody)).thenReturn(Optional.of(card));
        Mockito.when(cardRepository.verificationCardCVV(moneyTransferBody, card)).thenReturn(true);
        Mockito.when(cardRepository.verificationAccount(moneyTransferBody, card)).thenReturn(true);
        Mockito.when(cardRepository.verificationValidTill(moneyTransferBody, card)).thenReturn(true);

        validateCard.validateCardDate();
        long idActual = transferServices.transferMoney(moneyTransferBody);

        assertEquals(id, idActual);
        Mockito.verify(validateCard).validateCardDate();
    }

    @Test
    public void transferCard_shouldCallValidateConfirmOperation() {
        final ConfirmOperation confirmOperation = Mockito.mock(ConfirmOperation.class);
        final ValidateConfirmOperation validateConfirmOperation = Mockito.mock(ValidateConfirmOperation.class);
        final long id = 1L;
        Mockito.when(cardRepository.confirmOperationCode(confirmOperation)).thenReturn(true);
        Mockito.when(cardRepository.executeConfirmOperation(confirmOperation)).thenReturn(1L);

        long idActual = transferServices.confirmOperation(confirmOperation);
        validateConfirmOperation.validateOperation();

        assertEquals(id, idActual);
        Mockito.verify(validateConfirmOperation).validateOperation();
    }
}
