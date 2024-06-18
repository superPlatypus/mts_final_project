package ru.mts.accountservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.mts.accountservice.AbstractIntegrationTest;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
public class BankAccountControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;



    @Test
    void getBankAccountByIdTest() throws Exception {
        int userId = 1;
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/bankAccount/{id}", userId));
        resultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId));
    }

    @Test
    void addMoneyTest() throws Exception {
        int accountId = 1;
        BigDecimal amount = BigDecimal.valueOf(150000);

        mockMvc.perform(MockMvcRequestBuilders.post("/bankAccount/1/addMoney")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk());

    }

    @Test
    void isAllowTest() throws Exception {
        int userId = 1;

        BigDecimal amount = BigDecimal.valueOf(150000);
        mockMvc.perform(MockMvcRequestBuilders.get("/bankAccount/{id}/minusMoney/isAllow", userId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk());
    }


}
