package com.tdorosz.rewardpointservice.it;

import com.tdorosz.rewardpointservice.RewardPointServiceApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RewardPointServiceApplication.class})
@WebAppConfiguration
public class RewardPointsIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void shouldCalculateAndReturnRewardPoints() throws Exception {
        //given
        addTransaction("CID000001", "2021-05-07", "10.00");
        addTransaction("CID000001", "2021-05-08", "50.00");
        addTransaction("CID000001", "2021-05-10", "90.00");
        addTransaction("CID000001", "2021-06-07", "100.00");
        addTransaction("CID000001", "2021-06-08", "100.00");
        addTransaction("CID000001", "2021-06-10", "100.00");
        addTransaction("CID000001", "2021-07-07", "50.10");
        addTransaction("CID000001", "2021-07-08", "60.00");
        addTransaction("CID000001", "2021-07-10", "60.00");

        addTransaction("CID000002", "2021-05-07", "10.10");
        addTransaction("CID000002", "2021-05-08", "20.10");
        addTransaction("CID000002", "2021-05-10", "30.10");
        addTransaction("CID000002", "2021-06-07", "40.10");
        addTransaction("CID000002", "2021-06-08", "50.10");
        addTransaction("CID000002", "2021-06-10", "60.10");
        addTransaction("CID000002", "2021-07-07", "70.10");
        addTransaction("CID000002", "2021-07-08", "80.10");
        addTransaction("CID000002", "2021-07-10", "90.10");

        addTransaction("CID000004", "2021-05-07", "110.10");
        addTransaction("CID000004", "2021-05-08", "120.10");
        addTransaction("CID000004", "2021-05-10", "130.10");
        addTransaction("CID000004", "2021-06-07", "140.10");
        addTransaction("CID000004", "2021-06-08", "150.10");
        addTransaction("CID000004", "2021-06-10", "160.10");
        addTransaction("CID000004", "2021-07-07", "170.10");
        addTransaction("CID000004", "2021-07-08", "180.10");
        addTransaction("CID000004", "2021-07-10", "190.10");

        //when & then
        mockMvc.perform(
                get("/customer/CID000001/rewardpoints"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value("CID000001"))
                .andExpect(jsonPath("$.points.monthly.2021-05").value("40"))
                .andExpect(jsonPath("$.points.monthly.2021-06").value("150"))
                .andExpect(jsonPath("$.points.monthly.2021-07").value("20"))
                .andExpect(jsonPath("$.points.total").value("210"));

        mockMvc.perform(
                get("/customer/CID000002/rewardpoints"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value("CID000002"))
                .andExpect(jsonPath("$.points.monthly.2021-05").value("0"))
                .andExpect(jsonPath("$.points.monthly.2021-06").value("10"))
                .andExpect(jsonPath("$.points.monthly.2021-07").value("90"))
                .andExpect(jsonPath("$.points.total").value("100"));

        mockMvc.perform(
                get("/customer/CID000003/rewardpoints"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value("CID000003"))
                .andExpect(jsonPath("$.points.total").value("0"));

        mockMvc.perform(
                get("/customer/CID000004/rewardpoints"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value("CID000004"))
                .andExpect(jsonPath("$.points.monthly.2021-05").value("270"))
                .andExpect(jsonPath("$.points.monthly.2021-06").value("450"))
                .andExpect(jsonPath("$.points.monthly.2021-07").value("630"))
                .andExpect(jsonPath("$.points.total").value("1350"));
    }

    private void addTransaction(String customerId, final String date, String amount) throws Exception {
        mockMvc.perform(post("/transaction")
                .content(createBody(customerId, amount, date + "T10:00:00"))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    private String createBody(final String customerId, final String amount, final String creationDateTime) {
        return "{\n" +
                "    \"customerId\": \"" + customerId + "\",\n" +
                "    \"amount\": \"" + amount + "\",\n" +
                "    \"createDateTime\": \"" + creationDateTime + "\"\n" +
                "}";
    }
}
