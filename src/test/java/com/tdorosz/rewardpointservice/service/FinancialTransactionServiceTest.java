package com.tdorosz.rewardpointservice.service;

import com.tdorosz.rewardpointservice.model.FinancialTransaction;
import com.tdorosz.rewardpointservice.reposiitory.FinancialTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinancialTransactionServiceTest {

    @InjectMocks
    private FinancialTransactionService financialTransactionService;

    @Mock
    private FinancialTransactionRepository transactionRepository;

    @Test
    void shouldAddTransactionToRepository() {
        // given
        FinancialTransaction financialTransaction = new FinancialTransaction("1", "customerId", new BigDecimal("300"),
                LocalDateTime.parse("2021-01-03T10:30:00"));
        FinancialTransaction expectedResult = financialTransaction.withTransactionId("trx-id");
        when(transactionRepository.insert(any())).thenReturn(expectedResult);

        // when
        FinancialTransaction result = financialTransactionService.addTransaction(financialTransaction);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }
}