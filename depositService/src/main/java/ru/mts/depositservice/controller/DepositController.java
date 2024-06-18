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

    @GetMapping("/")
    public String home() {
        return "Hello from deposit service!!";
    }



    @GetMapping("/deposit/{id}")
    public Deposit getDeposit(@PathVariable int id) {

        return depositRepository.findById(id).orElseThrow( () -> new RuntimeException("Вклад не существует"));
    }



    @PostMapping("addDepositWithPercents")
    public ResponseEntity<Integer> addDepositWithPercents(
            @RequestParam("depositAccountId") int accountId,
            @RequestParam("depositTypeId") int depositTypeId,
            @RequestParam("depositAmount") BigDecimal depositAmount,
            @RequestParam("typePercentPaymentId") int typePercentPaymentId,
            @RequestParam("month") int month) {
        Deposit deposit = new Deposit(accountId, depositTypeId, depositAmount, typePercentPaymentId, month);
        int id = depositRepository.save(deposit).getId();
        return ResponseEntity.ok(id);
    }

    @PostMapping("addDepositWithCapitalization")
    public ResponseEntity<Integer> addDepositWithCapitalization(
            @RequestParam("depositAccountId") int accountId,
            @RequestParam("depositTypeId") int depositTypeId,
            @RequestParam("depositAmount") BigDecimal depositAmount,
            @RequestParam("month") int month) {
        Deposit deposit = new Deposit(accountId, depositTypeId, depositAmount, month);
        System.out.println(deposit);
        int id = depositRepository.save(deposit).getId();
        return ResponseEntity.ok(id);
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
