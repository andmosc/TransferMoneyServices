package ru.andmosc.transferMoney.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.andmosc.transferMoney.domain.Amount;
import ru.andmosc.transferMoney.domain.ConfirmOperation;
import ru.andmosc.transferMoney.domain.Transfer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class TestTransferController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    TransferController transferController;


    @Test
    public void postRequestTransfer_returnIsOK() throws Exception {

        Transfer moneyTransferBody = new Transfer("11", "11",
                "11", "11", new Amount(10L, "RUR"));

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
        ConfirmOperation confirmOperation = new ConfirmOperation("19", "0000");
        mockMvc.perform(
                        post("/confirmOperation")
                                .content(objectMapper.writeValueAsString(confirmOperation))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
