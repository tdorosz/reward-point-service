package com.tdorosz.rewardpointservice.controller;

import com.tdorosz.rewardpointservice.controller.request.AddTransactionRequest;
import com.tdorosz.rewardpointservice.model.FinancialTransaction;
import com.tdorosz.rewardpointservice.service.FinancialTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class FinancialTransactionController {

    private final FinancialTransactionService service;

    @PostMapping
    public FinancialTransaction addTransaction(@RequestBody AddTransactionRequest request) {
        return service.addTransaction(
                new FinancialTransaction(null, request.getCustomerId(), request.getAmount(), request.getCreateDateTime()));
    }

}
