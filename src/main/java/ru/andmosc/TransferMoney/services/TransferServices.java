package ru.andmosc.TransferMoney.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.andmosc.TransferMoney.dto.ConfirmOperation;
import ru.andmosc.TransferMoney.exception.ErrorConfirmOperation;
import ru.andmosc.TransferMoney.exception.ErrorVerificationCard;
import ru.andmosc.TransferMoney.models.MoneyTransfer;
import ru.andmosc.TransferMoney.repositories.CardRepository;

@Service
public class TransferServices {

    private final CardRepository cardRepository;

    @Autowired
    public TransferServices(CardRepository cardRepository) {

        this.cardRepository = cardRepository;
    }

    public ResponseEntity<?> verificationCard(MoneyTransfer moneyTransferBody) {

        if (!cardRepository.verificationNumberCard(moneyTransferBody)) {
            //TODO карта указана не верно
            throw new ErrorVerificationCard("Ошибка ввода номера карты: " + moneyTransferBody.getCardFromNumber());
        }
        if (!cardRepository.verificationCardCVV(moneyTransferBody)) {
            //TODO CVV указан не верно
            throw new ErrorVerificationCard("CVV указан не верно: " + moneyTransferBody.getCardFromCVV());
        }
        if (!cardRepository.verificationValidTill(moneyTransferBody)) {
            //TODO CVV указан не верно
            throw new ErrorVerificationCard("Ошибка ввода даты карты: " + moneyTransferBody.getCardFromValidTill());
        }
        if (!cardRepository.verificationAccount(moneyTransferBody)) {
            //TODO недостаточно средств на счете
            throw new ErrorVerificationCard("Недостаточно средств на карте: " + moneyTransferBody.getAmount().getValue());
        }
          // TODO номер операции и сумма операции
        return cardRepository.verificationCompleted(moneyTransferBody);
    }

    public ResponseEntity<?> confirmOperation(ConfirmOperation confirmOperationBody) {
        if (!cardRepository.confirmOperation(confirmOperationBody)) {
            //TODO код подтверждения не принят
           throw new ErrorConfirmOperation("Код подтверждения не принят: " + confirmOperationBody.getCode());
        }
        //TODO код подтверждения принят
        return cardRepository.confirmOperationCompleted(confirmOperationBody);
    }
}
