package com.tdorosz.rewardpointservice.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.tdorosz.rewardpointservice.model.CustomerPoints;
import com.tdorosz.rewardpointservice.model.FinancialTransaction;
import com.tdorosz.rewardpointservice.reposiitory.FinancialTransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RewardPointService {
    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    private final FinancialTransactionRepository financialTransactionRepository;
    private final RewardPointCalculator rewardPointCalculator;

    public CustomerPoints getPointsForCustomer(String customerId) {
        List<FinancialTransaction> financialTransactions = financialTransactionRepository.find(customerId);

        Map<String, Integer> pointsPerMonth = financialTransactions
                .stream()
                .collect(groupingBy(this::toYearMonthFormat)).entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> rewardPointCalculator.calculate(entry.getValue()), (a, b) -> a, TreeMap::new));

        Integer total = pointsPerMonth.values().stream()
                .reduce(Integer::sum)
                .orElse(0);

        return new CustomerPoints(total, pointsPerMonth);

    }

    private String toYearMonthFormat(FinancialTransaction financialTransaction) {
        return financialTransaction.getCreateDateTime().format(YEAR_MONTH_FORMATTER);
    }
}
