package ru.platypus.aggregator.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "customerServiceFeignClient", url = "localhost:8081")
public interface CustomerServiceFeignClient {

    @GetMapping("/")
    String hello();
}
