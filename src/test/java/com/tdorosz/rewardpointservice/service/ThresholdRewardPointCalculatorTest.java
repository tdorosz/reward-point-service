package com.tdorosz.rewardpointservice.service;

import com.tdorosz.rewardpointservice.model.FinancialTransaction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ThresholdRewardPointCalculatorTest {

    public static final String ANY_DATE = "2021-07-20T10:00:00";
    public static final String ANY_CUSTOMER_ID = "customerId";

    @ParameterizedTest
    @MethodSource
    void shouldCalculateRewardPoints(List<FinancialTransaction> financialTransactions, Integer expectedPoints) {
        //given & when
        Integer points = new ThresholdRewardPointCalculator().calculate(financialTransactions);

        //then
        assertThat(points).isEqualTo(expectedPoints);
    }

    private static Stream<Arguments> shouldCalculateRewardPoints() {
        return Stream.of(
                Arguments.arguments(List.of(
                        createFinancialTransaction("10.00"),
                        createFinancialTransaction("50.00")),
                        0),
                Arguments.arguments(List.of(
                        createFinancialTransaction("10.00"),
                        createFinancialTransaction("50.00")),
                        0),
                Arguments.arguments(List.of(
                        createFinancialTransaction("60.00"),
                        createFinancialTransaction("100.00")),
                        60),
                Arguments.arguments(List.of(
                        createFinancialTransaction("120.00")),
                        90),
                Arguments.arguments(List.of(
                        createFinancialTransaction("50.00"),
                        createFinancialTransaction("90.00"),
                        createFinancialTransaction("100.00"),
                        createFinancialTransaction("300.00")),
                        540)
        );
    }


    private static FinancialTransaction createFinancialTransaction(String amount) {
        return new FinancialTransaction(null, ANY_CUSTOMER_ID, new BigDecimal(amount), LocalDateTime.parse(ANY_DATE));
    }
}