package ru.platypus.aggregator.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Map;

@FeignClient(name = "accountServiceFeignClient", url = "localhost:8082", configuration = FeignConfig.class)
public interface AccountServiceFeignClient {
    @GetMapping("/bankAccount/{id}")
    Map<String,Object> getBankAccountById(@PathVariable int id);

    @PostMapping("/bankAccount{id}/addMoney")
    ResponseEntity<String> addMoney(@PathVariable int id, @RequestParam BigDecimal amount);
}
