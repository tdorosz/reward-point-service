package com.tdorosz.rewardpointservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tdorosz.rewardpointservice.model.CustomerPoints;
import com.tdorosz.rewardpointservice.model.FinancialTransaction;
import com.tdorosz.rewardpointservice.reposiitory.FinancialTransactionRepository;

@ExtendWith(MockitoExtension.class)
class RewardPointServiceTest {

    @InjectMocks
    private RewardPointService rewardPointService;

    @Mock
    private FinancialTransactionRepository transactionRepository;

    @Mock
    private RewardPointCalculator rewardPointCalculator;

    @Test
    void shouldCalculateRewardPointsForCustomer() {
        // given
        FinancialTransaction financialTransaction1 = new FinancialTransaction("1", "customerId", new BigDecimal("300"),
                LocalDateTime.parse("2021-01-03T10:30:00"));
        FinancialTransaction financialTransaction2 = new FinancialTransaction("2", "customerId", new BigDecimal("200"),
                LocalDateTime.parse("2021-02-04T10:30:00"));
        when(transactionRepository.find(any()))
                .thenReturn(List.of(financialTransaction1, financialTransaction2));
        when(rewardPointCalculator.calculate(eq(List.of(financialTransaction1)))).thenReturn(200);
        when(rewardPointCalculator.calculate(eq(List.of(financialTransaction2)))).thenReturn(300);

        TreeMap<String, Integer> treeMap = new TreeMap<>(Map.of("2021-01", 200, "2021-02", 300));

        CustomerPoints expectedResult = new CustomerPoints(500, treeMap);

        // when
        CustomerPoints customerPoints = rewardPointService
                .getPointsForCustomer("customerId");

        // then
        assertThat(customerPoints).isEqualTo(expectedResult);
    }
}