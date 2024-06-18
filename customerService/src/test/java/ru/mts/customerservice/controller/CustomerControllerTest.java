package ru.mts.customerservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.mts.customerservice.AbstractIntegrationTest;
import ru.mts.customerservice.entity.Customer;
import ru.mts.customerservice.service.CustomerService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
public class CustomerControllerTest extends AbstractIntegrationTest{
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    @Test
    void getAllCustomersTest() throws Exception {
        String expectedJson = "[{\"id\":1,\"phoneNumber\":\"+79661894542\",\"bankAccountId\":1}]";
        int customerId = 1;
        Customer mockCustomer = new Customer();
        mockCustomer.setIdCustomer(customerId);
        mockCustomer.setPhoneNumber("+79661894542");
        mockCustomer.setBank_account_id(1);
        when(customerService.getAllCustomers()).thenReturn(List.of(mockCustomer));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }


    @Test
    public void getCustomerByIdTest() throws Exception {
        int customerId = 1;
        Customer mockCustomer = new Customer();
        mockCustomer.setIdCustomer(customerId);
        mockCustomer.setPhoneNumber("+79661894542");
        mockCustomer.setBank_account_id(1);

        when(customerService.getCustomerById(anyInt())).thenReturn(mockCustomer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customerId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(mockCustomer.getPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankAccountId").value(mockCustomer.getBank_account_id()));
    }
    @Test
    public void getBankAccountByCustomerIdTest() throws Exception {
        int customerId = 1;
        int expectedBankAccountId = 12345;

        when(customerService.getBankAccountByCustomerId(anyInt())).thenReturn(expectedBankAccountId);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}/bankAccount", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(expectedBankAccountId)));
    }

    @Test
    public void getPhoneNumberByCustomerIdTest() throws Exception {
        int customerId = 1;
        String expectedPhoneNumber = "+79661894542";

        when(customerService.getPhoneNumberByCustomerId(anyInt())).thenReturn(expectedPhoneNumber);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}/phoneNumber", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedPhoneNumber));
    }

    @Test
    public void getDepositsByCustomerIdTest() throws Exception {
        int customerId = 1;
        List<Integer> expectedDeposits = List.of(1, 2, 3);

        when(customerService.getDepositsByCustomerId(anyInt())).thenReturn(expectedDeposits);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}/deposits", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }




}
