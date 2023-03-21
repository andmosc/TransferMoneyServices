package ru.andmosc.transferMoney.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.andmosc.transferMoney.exception.ErrorTransferCard;
import ru.andmosc.transferMoney.domain.Card;
import ru.andmosc.transferMoney.domain.Transfer;
import ru.andmosc.transferMoney.repository.CardRepository;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ValidateCard {
    private  CardRepository cardRepository;
    private Transfer transfer;
    private long id;

    public void validateCardDate() {
        Card card = cardRepository.verificationNumberCard(transfer)
                .orElseThrow(
                        () -> {
                            log.error("Ошибка ввода номера карты: {}", transfer.getCardFromNumber());
                            return new ErrorTransferCard("Ошибка ввода номера карты: " + transfer.getCardFromNumber(), id);
                        }
                );
        if (!cardRepository.verificationCardCVV(transfer, card)) {
            log.error("CVV указан не верно: {}", transfer.getCardFromCVV());
            throw new ErrorTransferCard("CVV указан не верно: " + transfer.getCardFromCVV(), id);
        }
        if (!cardRepository.verificationValidTill(transfer, card)) {
            log.error("Ошибка ввода даты карты: {}", transfer.getCardFromValidTill());
            throw new ErrorTransferCard("Ошибка ввода даты карты: " + transfer.getCardFromValidTill(), id);
        }
        if (!cardRepository.verificationAccount(transfer, card)) {
            log.error("Недостаточно средств на карте: {}", transfer.getAmount().getValue());
            throw new ErrorTransferCard("Недостаточно средств на карте: " + transfer.getAmount().getValue(), id);
        }
        log.info("Верификаия карты пройдена, id операции: {}", id);
    }
}
