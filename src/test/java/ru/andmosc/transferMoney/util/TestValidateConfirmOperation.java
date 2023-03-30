package ru.andmosc.transferMoney.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.exception.ErrorConfirmOperation;
import ru.andmosc.transferMoney.integration.DatabaseCards;
import ru.andmosc.transferMoney.repository.CardRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestValidateConfirmOperation {
    @Mock
    private CardRepository cardRepository;
    @Mock
    private ConfirmOperation confirmOperationBody;
    @InjectMocks
    private ValidateConfirmOperation validateConfirmOperation;
    @Test
    void validateOperation_noConfirmOperationCode() {
        Mockito.when(cardRepository.confirmOperationCode(confirmOperationBody)).thenReturn(false);
        Mockito.when(confirmOperationBody.getCode()).thenReturn("0101");
        Mockito.when(confirmOperationBody.getOperationId()).thenReturn("1");

        Throwable throwable = assertThrows(ErrorConfirmOperation.class,() ->
                validateConfirmOperation.validateOperation()
        );

        assertEquals("Код подтверждения не принят: 0101",throwable.getMessage());
    }
}