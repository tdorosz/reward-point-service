package com.tdorosz.rewardpointservice.service;

import com.tdorosz.rewardpointservice.model.FinancialTransaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ThresholdRewardPointCalculator implements RewardPointCalculator {

    private static final int SINGLE_POINTS_THRESHOLD = 50;
    private static final int DOUBLE_POINTS_THRESHOLD = 100;

    @Override
    public Integer calculate(List<FinancialTransaction> financialTransactions) {
        return financialTransactions.stream()
                .mapToInt(this::mapToPoints)
                .sum();
    }

    private Integer mapToPoints(FinancialTransaction financialTransaction) {
        int amount = financialTransaction.getAmount().intValue();
        int doublePoints = Math.max(amount - DOUBLE_POINTS_THRESHOLD, 0);
        int singlePoints = Math.max(amount - doublePoints - SINGLE_POINTS_THRESHOLD, 0);
        return doublePoints * 2 + singlePoints;
    }

}
