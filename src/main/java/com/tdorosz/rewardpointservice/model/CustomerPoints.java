package com.tdorosz.rewardpointservice.model;

import lombok.Value;

import java.util.Map;

@Value
public class CustomerPoints {
    Integer total;
    Map<String, Integer> monthly;
}
