package ru.platypus.aggregator.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "smsServiceFeignClient", url = "localhost:8084", configuration = FeignConfig.class)
public interface SmsServiceFeignClient {
    @PostMapping("/api/sms/verify")
    ResponseEntity<String> verify(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("code") String code);

    @PostMapping("/api/sms/send")
    ResponseEntity<String> send(@RequestParam("phoneNumber") String phoneNumber);
}