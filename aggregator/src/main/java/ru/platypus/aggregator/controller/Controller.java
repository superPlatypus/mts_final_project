package ru.platypus.aggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platypus.aggregator.feign.CustomerServiceFeignClient;

@RestController
public class Controller {
    private final CustomerServiceFeignClient customerServiceFeignClient;


    public Controller(CustomerServiceFeignClient customerServiceFeignClient) {
        this.customerServiceFeignClient = customerServiceFeignClient;
    }

    @GetMapping("/")
    public String index() {
        return customerServiceFeignClient.hello();
    }
}
