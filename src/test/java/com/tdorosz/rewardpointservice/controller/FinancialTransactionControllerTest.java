package com.tdorosz.rewardpointservice.controller;

import com.tdorosz.rewardpointservice.model.FinancialTransaction;
import com.tdorosz.rewardpointservice.service.FinancialTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FinancialTransactionController.class)
class FinancialTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialTransactionService financialTransactionService;

    @Test
    void shouldReturnRewardPointsForCustomer() throws Exception {
        // given
        when(financialTransactionService.addTransaction(any()))
                .thenReturn(new FinancialTransaction("id-1", "CID000001", new BigDecimal("90.01"), LocalDateTime.parse("2021-08-07T10:12:13")));

        // when & then
        mockMvc.perform(post("/transaction").contentType(APPLICATION_JSON).content(createBody()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value("CID000001"))
                .andExpect(jsonPath("$.amount").value("90.01"))
                .andExpect(jsonPath("$.createDateTime").value("2021-08-07T10:12:13"))
                .andExpect(jsonPath("$.transactionId").value("id-1"));
    }

    private String createBody() {
        return "{\n" +
                "    \"customerId\": \"customer1\",\n" +
                "    \"amount\": \"90.01\",\n" +
                "    \"createDateTime\": \"2021-08-07T10:12:13\"\n" +
                "}";
    }
}