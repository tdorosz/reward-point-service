package com.tdorosz.rewardpointservice.service;

import org.springframework.stereotype.Service;

import com.tdorosz.rewardpointservice.model.FinancialTransaction;
import com.tdorosz.rewardpointservice.reposiitory.FinancialTransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinancialTransactionService {

    private final FinancialTransactionRepository transactionRepository;

    public FinancialTransaction addTransaction(FinancialTransaction transaction) {
        return transactionRepository.insert(transaction);
    }
}
