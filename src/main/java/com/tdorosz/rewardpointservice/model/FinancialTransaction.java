package com.tdorosz.rewardpointservice.model;

import lombok.Value;
import lombok.With;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@With
public class FinancialTransaction {
    String transactionId;
    String customerId;
    BigDecimal amount;
    LocalDateTime createDateTime;
}
