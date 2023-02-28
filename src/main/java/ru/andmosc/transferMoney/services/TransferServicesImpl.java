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
public class TransferServicesImpl implements TransferServices{

    private final CardRepository cardRepository;

    @Autowired
    public TransferServicesImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<?> verificationCard(MoneyTransfer moneyTransferBody) {
        int id = cardRepository.getOperationID();
        if (!cardRepository.verificationNumberCard(moneyTransferBody)) {
            log.error("Ошибка ввода номера карты: {}", moneyTransferBody.getCardFromNumber());
            throw new ErrorVerificationCard("Ошибка ввода номера карты: " + moneyTransferBody.getCardFromNumber(),id);
        }
        if (!cardRepository.verificationCardCVV(moneyTransferBody)) {
            log.error("CVV указан не верно: {}", moneyTransferBody.getCardFromCVV());
            throw new ErrorVerificationCard("CVV указан не верно: " + moneyTransferBody.getCardFromCVV(),id);
        }
        if (!cardRepository.verificationValidTill(moneyTransferBody)) {
            log.error("Ошибка ввода даты карты: {}", moneyTransferBody.getCardFromValidTill());
            throw new ErrorVerificationCard("Ошибка ввода даты карты: " + moneyTransferBody.getCardFromValidTill(),id);
        }
        if (!cardRepository.verificationAccount(moneyTransferBody)) {
            log.error("Недостаточно средств на карте: {}", moneyTransferBody.getAmount().getValue());
            throw new ErrorVerificationCard("Недостаточно средств на карте: " + moneyTransferBody.getAmount().getValue(),id);
        }
        log.info("Верификаия карты пройдена, id операции: {}", id);
        return cardRepository.saveVerification(moneyTransferBody);
    }

    public ResponseEntity<?> confirmOperation(ConfirmOperation confirmOperationBody) {
        if (!cardRepository.confirmOperation(confirmOperationBody)) {
            log.error("Код подтверждения не принят: {}", confirmOperationBody.getCode());
            throw new ErrorConfirmOperation("Код подтверждения не принят: " + confirmOperationBody.getCode()
                    ,Integer.parseInt(confirmOperationBody.getOperationId()));
        }
        log.info("{}",cardRepository.operationByCard(confirmOperationBody));
        return cardRepository.confirmOperationCompleted(confirmOperationBody);
    }
}
