package ru.mts.accountservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mts.accountservice.entity.BankAccount;
import ru.mts.accountservice.repository.BankAccountRepository;

import java.math.BigDecimal;

@RestController
public class BankAccountController {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("/bankAccount/{id}")
    public BankAccount getBankAccountById(@PathVariable int id) {
        return bankAccountRepository.findById(id).get();
    }

    @PostMapping("/bankAccount{id}/addMoney")
    public ResponseEntity<String> addMoney(@PathVariable int id, @RequestParam BigDecimal amount) {
        BankAccount bankAccount = bankAccountRepository.findById(id).get();
        BigDecimal currentAmount = bankAccount.getAmount();
        bankAccount.setAmount(currentAmount.add(amount));
        bankAccountRepository.save(bankAccount);
        return ResponseEntity.ok("Счет успешно пополнен");
    }
}
