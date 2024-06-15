package ru.platypus.aggregator.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platypus.aggregator.dto.Signin;

import java.util.List;
import java.util.Map;


@FeignClient(name = "customerServiceFeignClient", url = "localhost:8081", configuration = FeignConfig.class)
public interface CustomerServiceFeignClient {

    @GetMapping("/currentCustomer")
    String getCurrentCustomer();

    @GetMapping("customers/{id}")
    Map<String, Object> getCustomerById(@PathVariable int id);

    @GetMapping("customers")
    List<Map<String, Object>> getAllCustomers();

    @PostMapping("/signin")
    ResponseEntity<String> signin(@RequestBody Signin signin);

    @GetMapping("customers/{id}/bankAccount")
    int getBankAccountByCustomerId(@PathVariable int id);

    @GetMapping("customers/{id}/phoneNumber")
    String getPhoneNumberByCustomerId(@PathVariable int id);

    @GetMapping("customers/{id}/deposits")
    List<Integer> getDepositsByCustomerId(@PathVariable int id);

    @PostMapping("/customersDeposit/add")
    Map<String, Object> addCustomerDeposit(@RequestParam int customerId, @RequestParam int depositId);

}
