package ru.platypus.aggregator.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "accountServiceFeignClient", url = "localhost:8082", configuration = FeignConfig.class)
public interface AccountServiceFeignClient {
    @GetMapping("/bankAccount/{id}")
    public Map<String,Object> getBankAccountById(@PathVariable int id);
}
