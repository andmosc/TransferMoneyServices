package ru.andmosc.transferMoney.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.andmosc.transferMoney.models.Amount;
import ru.andmosc.transferMoney.models.MoneyTransfer;
import ru.andmosc.transferMoney.repositories.CardRepository;
import ru.andmosc.transferMoney.services.TransferServicesImpl;
import ru.andmosc.transferMoney.util.OperationsTransfer;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
public class TestTransferController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private TransferController transferController;

    @MockBean
    private CardRepository cardRepository;
    @MockBean
    private TransferServicesImpl transferServices;


    @Test
    public void testPostRequestTransfer() throws Exception {
        MoneyTransfer moneyTransferBody = new MoneyTransfer("cardFromNumber", "cardToNumber",
                "cardFromCVV", "cardFromValidTill", new Amount(0L, "currency"));

//        ResponseEntity<?> operationID = ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("OperationID", 1));
//
//        Mockito.when(transferServices.verificationCard(moneyTransferBody))
//                .thenReturn(operationID);

        transferServices.verificationCard(moneyTransferBody);

        mockMvc.perform(
                post("/transfer")
                        .content(objectMapper.writeValueAsString(moneyTransferBody))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }
}
