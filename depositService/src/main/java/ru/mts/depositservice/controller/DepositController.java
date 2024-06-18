package ru.mts.depositservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mts.depositservice.entity.Deposit;
import ru.mts.depositservice.entity.DepositTypes;
import ru.mts.depositservice.entity.TypesPercentPayment;
import ru.mts.depositservice.repository.DepositRepository;
import ru.mts.depositservice.repository.DepositTypesRepository;
import ru.mts.depositservice.repository.TypesPercentPaymentRepository;
import ru.mts.depositservice.service.DepositService;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class DepositController {

    private final DepositTypesRepository depositTypesRepository;
    private final TypesPercentPaymentRepository typesPercentPaymentRepository;
    private final DepositRepository depositRepository;
    private final DepositService depositService;

    public DepositController(DepositTypesRepository depositTypesRepository, TypesPercentPaymentRepository typesPercentPaymentRepository, DepositRepository depositRepository, DepositService depositService) {
        this.depositTypesRepository = depositTypesRepository;
        this.typesPercentPaymentRepository = typesPercentPaymentRepository;
        this.depositRepository = depositRepository;
        this.depositService = depositService;
    }

    @GetMapping("/deposit/{id}")
    public Deposit getDeposit(@PathVariable int id) {
        return depositService.getDeposit(id);
    }



    @PostMapping("addDepositWithPercents")
    public ResponseEntity<Integer> addDepositWithPercents(
            @RequestParam("depositAccountId") int accountId,
            @RequestParam("depositTypeId") int depositTypeId,
            @RequestParam("depositAmount") BigDecimal depositAmount,
            @RequestParam("typePercentPaymentId") int typePercentPaymentId,
            @RequestParam("month") int month) {
        return depositService.addDepositWithPercents(accountId, depositTypeId,depositAmount, typePercentPaymentId,month);

    }

    @PostMapping("addDepositWithCapitalization")
    public ResponseEntity<Integer> addDepositWithCapitalization(
            @RequestParam("depositAccountId") int accountId,
            @RequestParam("depositTypeId") int depositTypeId,
            @RequestParam("depositAmount") BigDecimal depositAmount,
            @RequestParam("month") int month) {
        return depositService.addDepositWithCapitalization(accountId, depositTypeId, depositAmount, month);

    }

    @PostMapping("deposit/{id}/addMoney")
    public  ResponseEntity<Integer> addMoney(@PathVariable int id, @RequestParam("amount") BigDecimal amount) {
        depositService.addMoney(id, amount);
        return ResponseEntity.ok(id);
    }

    @PostMapping("deposit/{id}/delete")
    public void deleteDeposit(@PathVariable int id) {
        depositService.deleteDeposit(id);
    }
}
