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
    ResponseEntity<Integer> addDepositWithPercents(
            @RequestParam("depositAccountId") int accountId,
            @RequestParam("depositTypeId") int depositTypeId,
            @RequestParam("depositAmount") BigDecimal depositAmount,
            @RequestParam("typePercentPaymentId") int typePercentPaymentId,
            @RequestParam("month") int month);

    @PostMapping("addDepositWithCapitalization")
    ResponseEntity<Integer> addDepositWithCapitalization(
            @RequestParam("depositAccountId") int accountId,
            @RequestParam("depositTypeId") int depositTypeId,
            @RequestParam("depositAmount") BigDecimal depositAmount,
            @RequestParam("month") int month);

    @GetMapping("/deposit/{id}")
    Map<String, Object> getDeposit(@PathVariable int id);

    @PostMapping("/newRequest")
    ResponseEntity<Integer> newRequest(@RequestParam("customerId") int customerId, @RequestParam("depositId") int depositId);

    @PostMapping("request/{id}/updateStatus")
    ResponseEntity<String> updateStatus(@PathVariable("id") int requestId, @RequestParam("statusId") int statusId);

    @GetMapping("requestByDepositId")
    ResponseEntity<Integer> getRequestByDepositId(@RequestParam("depositId") int depositId);

    @PostMapping("deposit/{id}/addMoney")
    ResponseEntity<Integer> addMoney(@PathVariable int id, @RequestParam("amount") BigDecimal amount);

    @PostMapping("deposit/{id}/delete")
    void deleteDeposit(@PathVariable int id);

    @GetMapping("requestStatus")
    ResponseEntity<Integer> getRequestStatus(@RequestParam("requestId") int requestId);

    @GetMapping("rejectedRequestsByCustomerId")
    List<Map<String, Object>> getRejectedRequests(@RequestParam("customerId") int customerId);

    @GetMapping("/request{id}")
    Map<String, Object> getRequest(@PathVariable("id") int requestId);

    @PostMapping("/request{id}/delete")
    void deleteRequest(@PathVariable("id") int requestId);
}
