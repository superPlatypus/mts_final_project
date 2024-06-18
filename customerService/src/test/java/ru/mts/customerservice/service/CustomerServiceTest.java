package ru.mts.customerservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.mts.customerservice.AbstractIntegrationTest;
import ru.mts.customerservice.dto.Signin;
import ru.mts.customerservice.jwt.JWTUtilty;

import static org.mockito.Mockito.when;

public class CustomerServiceTest extends AbstractIntegrationTest {
    @Autowired
    private CustomerService customerService;
    @MockBean
    private JWTUtilty jwtUtility;

    @Test
    void signinTest(){
        Signin signin = new Signin();
        signin.setPhoneNumber("+79661894542");
        customerService.signin(signin);
        int customerId = 1;
        when(jwtUtility.generateJwtToken(customerId)).thenReturn("mocked-jwt-token");
        Assertions.assertEquals("mocked-jwt-token", customerService.signin(signin).getBody());
    }

    @Test
    void getAllCustomersTest(){
        Assertions.assertNotNull(customerService.getAllCustomers());
        Assertions.assertTrue(customerService.getAllCustomers().size() == 2);
    }

    @Test
    void getCustomerByIdTest(){
        Assertions.assertNotNull(customerService.getCustomerById(1));
    }

    @Test
    void getBankAccountByCustomerIdTest(){
        Assertions.assertNotNull(customerService.getBankAccountByCustomerId(1));
    }

    @Test
    void getPhoneNumberByCustomerIdTest(){
        Assertions.assertNotNull(customerService.getPhoneNumberByCustomerId(1));
    }

    @Test
    void getDepositsByCustomerIdTest(){
        Assertions.assertNotNull(customerService.getDepositsByCustomerId(1));
    }

    @Test
    void addCustomerDeposit(){
        Assertions.assertNotNull(customerService.addCustomerDeposit(1, 1));
    }


}
