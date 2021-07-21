package com.tdorosz.rewardpointservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdorosz.rewardpointservice.controller.response.RewardPointForCustomerResponse;
import com.tdorosz.rewardpointservice.model.CustomerPoints;
import com.tdorosz.rewardpointservice.service.RewardPointService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("customer/{customerId}/rewardpoints")
@RequiredArgsConstructor
public class RewardPointController {

    private final RewardPointService rewardPointService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public RewardPointForCustomerResponse rewardPointsForCustomer(@PathVariable String customerId) {
        CustomerPoints customerPoints = rewardPointService.getPointsForCustomer(customerId);

        return new RewardPointForCustomerResponse(customerId, customerPoints);
    }
}
