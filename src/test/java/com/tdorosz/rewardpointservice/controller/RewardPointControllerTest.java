package com.tdorosz.rewardpointservice.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tdorosz.rewardpointservice.model.CustomerPoints;
import com.tdorosz.rewardpointservice.service.RewardPointService;

@WebMvcTest(RewardPointController.class)
class RewardPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardPointService rewardPointService;

    @Test
    void shouldReturnRewardPointsForCustomer() throws Exception {
        // given
        when(rewardPointService.getPointsForCustomer(anyString()))
                .thenReturn(new CustomerPoints(520, Map.of("2021-01", 100,
                        "2021-02", 120,
                        "2021-03", 300)));

        // when & then
        mockMvc.perform(get("/customer/CID000001/rewardpoints"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value("CID000001"))
                .andExpect(jsonPath("$.points.total").value("520"))
                .andExpect(jsonPath("$.points.monthly.2021-01").value("100"))
                .andExpect(jsonPath("$.points.monthly.2021-02").value("120"))
                .andExpect(jsonPath("$.points.monthly.2021-03").value("300"));
    }
}