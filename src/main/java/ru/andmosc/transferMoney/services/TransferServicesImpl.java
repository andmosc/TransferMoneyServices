package ru.andmosc.transferMoney.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andmosc.transferMoney.domain.ConfirmOperation;
import ru.andmosc.transferMoney.domain.Transfer;
import ru.andmosc.transferMoney.repository.CardRepository;
import ru.andmosc.transferMoney.util.ValidateCard;
import ru.andmosc.transferMoney.util.ValidateConfirmOperation;

@Service
@AllArgsConstructor
public class TransferServicesImpl implements TransferServices {
    private final CardRepository cardRepository;

    public long transferMoney(Transfer transferBody) {
        long id = cardRepository.getOperationID();

        ValidateCard validateCard = new ValidateCard(cardRepository, transferBody, id);
        validateCard.validateCardDate();

        cardRepository.saveTransferOperation(transferBody, id);
        return id;
    }


    public long confirmOperation(ConfirmOperation confirmOperationBody) {
        ValidateConfirmOperation validateConfirmOperation = new ValidateConfirmOperation(cardRepository, confirmOperationBody);
        validateConfirmOperation.validateOperation();

        return cardRepository.executeConfirmOperation(confirmOperationBody);
    }
}
