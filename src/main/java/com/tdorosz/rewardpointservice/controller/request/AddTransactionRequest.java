package com.tdorosz.rewardpointservice.controller.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AddTransactionRequest {
    private String customerId;
    private BigDecimal amount;
    private LocalDateTime createDateTime;
}
