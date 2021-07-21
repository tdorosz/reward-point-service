package com.tdorosz.rewardpointservice.controller.response;

import com.tdorosz.rewardpointservice.model.CustomerPoints;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardPointForCustomerResponse {
    String customerId;
    CustomerPoints points;
}
