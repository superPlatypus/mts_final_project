package ru.mts.depositservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mts.depositservice.entity.Deposit;
import ru.mts.depositservice.entity.DepositTypes;
import ru.mts.depositservice.entity.TypesPercentPayment;
import ru.mts.depositservice.repository.DepositRepository;
import ru.mts.depositservice.repository.DepositTypesRepository;
import ru.mts.depositservice.repository.TypesPercentPaymentRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class DepositController {

    private final DepositTypesRepository depositTypesRepository;
    private final TypesPercentPaymentRepository typesPercentPaymentRepository;
    private final DepositRepository depositRepository;

    public DepositController(DepositTypesRepository depositTypesRepository, TypesPercentPaymentRepository typesPercentPaymentRepository, DepositRepository depositRepository) {
        this.depositTypesRepository = depositTypesRepository;
        this.typesPercentPaymentRepository = typesPercentPaymentRepository;
        this.depositRepository = depositRepository;
    }

    @GetMapping("/")
    public String home() {
        return "Hello from deposit service!!";
    }



    @GetMapping("/deposit/{id}")
    public Deposit getDeposit(@PathVariable int id) {
        return depositRepository.findById(id).orElseThrow( () -> new RuntimeException("Вклад не существует"));
    }


    @PostMapping("addDepositWithPercents")
    public ResponseEntity<String> addDepositWithPercents(
            @RequestParam("depositAccountId") int accountId,
            @RequestParam("depositTypeId") int depositTypeId,
            @RequestParam("depositAmount") BigDecimal depositAmount,
            @RequestParam("typePercentPaymentId") int typePercentPaymentId,
            @RequestParam("month") int month) {
        Deposit deposit = new Deposit(accountId, depositTypeId, depositAmount, typePercentPaymentId, month);
        depositRepository.save(deposit);
        return ResponseEntity.ok("Deposit added successfully");
    }

    @PostMapping("addDepositWithCapitalization")
    public ResponseEntity<String> addDepositWithCapitalization(
            @RequestParam("depositAccountId") int accountId,
            @RequestParam("depositTypeId") int depositTypeId,
            @RequestParam("depositAmount") BigDecimal depositAmount,
            @RequestParam("month") int month) {
        Deposit deposit = new Deposit(accountId, depositTypeId, depositAmount, month);
        System.out.println(deposit);
        depositRepository.save(deposit);
        return ResponseEntity.ok("Deposit added successfully");
    }
}
