package com.tdorosz.rewardpointservice.reposiitory;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tdorosz.rewardpointservice.model.FinancialTransaction;

@Service
public class InMemoryFinancialTransactionRepository implements FinancialTransactionRepository {

    private final List<FinancialTransaction> transactions = new ArrayList<>();

    public synchronized List<FinancialTransaction> find(String customerId) {
        return transactions.stream()
                .filter(transaction -> Objects.equals(transaction.getCustomerId(), customerId))
                .collect(toList());
    }

    @Override
    public synchronized FinancialTransaction insert(FinancialTransaction transaction) {
        String id = UUID.randomUUID().toString();
        transaction = transaction.withTransactionId(id);

        transactions.add(transaction);

        return transaction;
    }
}
