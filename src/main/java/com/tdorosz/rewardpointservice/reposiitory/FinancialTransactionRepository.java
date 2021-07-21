package com.tdorosz.rewardpointservice.reposiitory;

import java.util.List;

import com.tdorosz.rewardpointservice.model.FinancialTransaction;

public interface FinancialTransactionRepository {
    List<FinancialTransaction> find(String customerId);
    FinancialTransaction insert(FinancialTransaction transaction);
}
