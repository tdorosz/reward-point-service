package com.tdorosz.rewardpointservice.service;

import com.tdorosz.rewardpointservice.model.FinancialTransaction;

import java.util.List;

public interface RewardPointCalculator {
    Integer calculate(List<FinancialTransaction> financialTransactions);
}
