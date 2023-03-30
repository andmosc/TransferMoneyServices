package ru.andmosc.transferMoney.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.andmosc.transferMoney.domain.Card;
import ru.andmosc.transferMoney.dto.Amount;
import ru.andmosc.transferMoney.dto.Transfer;
import ru.andmosc.transferMoney.exception.ErrorTransferCard;
import ru.andmosc.transferMoney.repository.CardRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestValidateCard {
    @Mock
    private CardRepository cardRepository;
    @Mock
    private Transfer transfer;
    @Mock
    private Card card;
    @InjectMocks
    private ValidateCard validateCard;
    private long id;

    @Test
    void validateCardDate_noVerificationNumberCard() {
        Mockito.when(cardRepository.verificationNumberCard(transfer)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ErrorTransferCard.class, () ->
                validateCard.validateCardDate()
        );

        assertEquals("Ошибка ввода номера карты: null", throwable.getMessage());
    }

    @Test
    void validateCardDate_noVerificationCardCVV() {
        Mockito.when(cardRepository.verificationNumberCard(transfer)).thenReturn(Optional.of(card));
        Mockito.when(cardRepository.verificationCardCVV(transfer, card)).thenReturn(false);

        Throwable throwable = assertThrows(ErrorTransferCard.class, () ->
                validateCard.validateCardDate()
        );

        assertEquals("CVV указан не верно: null", throwable.getMessage());
    }

    @Test
    void validateCardDate_noValidTill() {
        Mockito.when(cardRepository.verificationNumberCard(transfer)).thenReturn(Optional.of(card));
        Mockito.when(cardRepository.verificationCardCVV(transfer, card)).thenReturn(true);
        Mockito.when(cardRepository.verificationValidTill(transfer, card)).thenReturn(false);

        Throwable throwable = assertThrows(ErrorTransferCard.class, () ->
                validateCard.validateCardDate()
        );

        assertEquals("Ошибка ввода даты карты: null", throwable.getMessage());
    }

    @Test
    void validateCardDate_noVerificationAccount() {
        Amount amountActual = new Amount(1L, "RUR");

        Mockito.when(cardRepository.verificationNumberCard(transfer)).thenReturn(Optional.of(card));
        Mockito.when(cardRepository.verificationCardCVV(transfer, card)).thenReturn(true);
        Mockito.when(cardRepository.verificationValidTill(transfer, card)).thenReturn(true);
        Mockito.when(cardRepository.verificationAccount(transfer, card)).thenReturn(false);

        Mockito.when(transfer.getAmount()).thenReturn(amountActual);

        Long value = transfer.getAmount().getValue();

        Throwable throwable = assertThrows(ErrorTransferCard.class, () ->
                validateCard.validateCardDate()
        );

        assertEquals("Недостаточно средств на карте: " + value, throwable.getMessage());
    }
}