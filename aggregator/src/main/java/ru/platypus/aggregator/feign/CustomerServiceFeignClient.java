package ru.platypus.aggregator.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String getPhoneNumberByCustomerId(@PathVariable int id);
}
