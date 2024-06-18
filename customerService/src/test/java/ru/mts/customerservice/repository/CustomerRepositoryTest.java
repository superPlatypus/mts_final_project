package ru.mts.customerservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.customerservice.AbstractIntegrationTest;
import ru.mts.customerservice.entity.Customer;
import ru.mts.customerservice.repostitory.CustomerRepository;

import java.util.Optional;

public class CustomerRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    void findAllRepoTest(){
        System.out.println(customerRepository.findAll());
        Assertions.assertEquals(2, customerRepository.findAll().size());
    }

    @Test
    @Transactional
    void findByPhoneNumberTest(){
        String phoneNumber = "+79661894542";
        Optional<Customer> foundCustomer = customerRepository.findByPhoneNumber(phoneNumber);
        Assertions.assertTrue(foundCustomer.isPresent());
    }
}
