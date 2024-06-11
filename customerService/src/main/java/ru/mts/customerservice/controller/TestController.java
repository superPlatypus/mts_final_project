package ru.mts.customerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.customerservice.repostitory.CustomerRepository;

@RestController
public class TestController {
    private final CustomerRepository customerRepository;

    public TestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/")
    public String home(){
        return customerRepository.findAll().toString();
    }
}
