package ru.andmosc.transferMoney.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.andmosc.transferMoney.dto.ConfirmOperation;
import ru.andmosc.transferMoney.models.Amount;
import ru.andmosc.transferMoney.models.MoneyTransfer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
public class TestTransferController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransferController transferController;


    @Test
    public void testPostRequestTransfer_returnIsOK() throws Exception {
        MoneyTransfer moneyTransferBody = new MoneyTransfer("cardFromNumber", "cardToNumber",
                "cardFromCVV", "cardFromValidTill", new Amount(0L, "currency"));

 /*       Mockito.when(transferServices.verificationCard(moneyTransferBody))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("OperationID", 1)));

        ResponseEntity<?> responseEntity = transferServices.verificationCard(moneyTransferBody);
*/

        mockMvc.perform(

                        post("/transfer")
                                .content(objectMapper.writeValueAsString(moneyTransferBody))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testPostRequestConfirmOperation_returnIsOK() throws Exception {
        ConfirmOperation confirmOperation = new ConfirmOperation("1", "code");
        mockMvc.perform(

                        post("/confirmOperation")
                                .content(objectMapper.writeValueAsString(confirmOperation))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
