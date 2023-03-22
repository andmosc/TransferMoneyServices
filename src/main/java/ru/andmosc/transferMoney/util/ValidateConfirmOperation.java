package ru.andmosc.transferMoney.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.andmosc.transferMoney.domain.ConfirmOperation;
import ru.andmosc.transferMoney.exception.ErrorConfirmOperation;
import ru.andmosc.transferMoney.repository.CardRepository;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ValidateConfirmOperation {
    private CardRepository cardRepository;
    private ConfirmOperation confirmOperationBody;

    public void validateOperation() {
        if (!cardRepository.confirmOperationCode(confirmOperationBody)) {
            log.error("Код подтверждения не принят: {}", confirmOperationBody.getCode());
            throw new ErrorConfirmOperation("Код подтверждения не принят: " + confirmOperationBody.getCode()
                    , Long.parseLong(confirmOperationBody.getOperationId()));
        }
        log.info("{}", cardRepository.executeOperation(confirmOperationBody));
    }
}
