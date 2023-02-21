package ru.andmosc.transferMoney.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.exception.ErrorConfirmOperation;
import ru.andmosc.transferMoney.exception.ErrorVerificationCard;
import ru.andmosc.transferMoney.models.MoneyTransfer;
import ru.andmosc.transferMoney.repositories.CardRepository;

@Service
@Slf4j
public class TransferServices {

    private final CardRepository cardRepository;

    @Autowired
    public TransferServices(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<?> verificationCard(MoneyTransfer moneyTransferBody) {

        if (!cardRepository.verificationNumberCard(moneyTransferBody)) {
            log.error("Ошибка ввода номера карты: {}", moneyTransferBody.getCardFromNumber());
            throw new ErrorVerificationCard("Ошибка ввода номера карты: " + moneyTransferBody.getCardFromNumber());
        }
        if (!cardRepository.verificationCardCVV(moneyTransferBody)) {
            log.error("CVV указан не верно: {}", moneyTransferBody.getCardFromCVV());
            throw new ErrorVerificationCard("CVV указан не верно: " + moneyTransferBody.getCardFromCVV());
        }
        if (!cardRepository.verificationValidTill(moneyTransferBody)) {
            log.error("Ошибка ввода даты карты: {}", moneyTransferBody.getCardFromValidTill());
            throw new ErrorVerificationCard("Ошибка ввода даты карты: " + moneyTransferBody.getCardFromValidTill());
        }
        if (!cardRepository.verificationAccount(moneyTransferBody)) {
            log.error("Недостаточно средств на карте: {}", moneyTransferBody.getAmount().getValue());
            throw new ErrorVerificationCard("Недостаточно средств на карте: " + moneyTransferBody.getAmount().getValue());
        }
        log.info("Верификаия карты пройдена: {}", moneyTransferBody.getCardFromNumber());
        return cardRepository.verificationCompleted(moneyTransferBody);
    }

    public ResponseEntity<?> confirmOperation(ConfirmOperation confirmOperationBody) {
        if (!cardRepository.confirmOperation(confirmOperationBody)) {
            log.error("Код подтверждения не принят: {}", confirmOperationBody.getCode());
            throw new ErrorConfirmOperation("Код подтверждения не принят: " + confirmOperationBody.getCode());
        }
        log.info("{}",cardRepository.operationByCard(confirmOperationBody));
        return cardRepository.confirmOperationCompleted(confirmOperationBody);
    }
}
