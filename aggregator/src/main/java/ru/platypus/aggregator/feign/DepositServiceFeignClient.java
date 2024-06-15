package ru.platypus.aggregator.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(name = "depositServiceFeignClient", url = "localhost:8083", configuration = FeignConfig.class)
public interface DepositServiceFeignClient {
    @GetMapping("depositTypes")
    List<Map<String, Object>> getDepositTypes();

    @GetMapping("depositTypes/{id}")
    Map<String, Object> getDepositType(@PathVariable int id);

    @GetMapping("typesPercentPayment")
    List<Map<String, Object>> getTypesPercentPayment();

    @GetMapping("typesPercentPayment/{id}")
    Map<String, Object> getTypesPercentPaymentById(@PathVariable int id);

    @PostMapping("addDepositWithPercents")
    ResponseEntity<String> addDepositWithPercents(
            @RequestParam("depositAccountId") int accountId,
            @RequestParam("depositTypeId") int depositTypeId,
            @RequestParam("depositAmount") BigDecimal depositAmount,
            @RequestParam("typePercentPaymentId") int typePercentPaymentId,
            @RequestParam("month") int month);

    @PostMapping("addDepositWithCapitalization")
    ResponseEntity<String> addDepositWithCapitalization(
            @RequestParam("depositAccountId") int accountId,
            @RequestParam("depositTypeId") int depositTypeId,
            @RequestParam("depositAmount") BigDecimal depositAmount,
            @RequestParam("month") int month);

    @GetMapping("/deposit/{id}")
    Map<String, Object> getDeposit(@PathVariable int id);
}
