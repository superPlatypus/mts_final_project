package ru.mts.accountservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mts.accountservice.entity.BankAccount;
import ru.mts.accountservice.repository.BankAccountRepository;
import ru.mts.accountservice.service.AccountService;

import java.math.BigDecimal;

@RestController
public class BankAccountController {
    private final BankAccountRepository bankAccountRepository;
    private final AccountService accountService;

    public BankAccountController(BankAccountRepository bankAccountRepository, AccountService accountService) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountService = accountService;
    }

    @GetMapping("currentBankAccountId")
    public Integer getCurrentBankAccount() {
        return accountService.getCurrentBankAccount();
    }

    @GetMapping("/bankAccount/{id}")
    public BankAccount getBankAccountById(@PathVariable int id) {
        return accountService.getBankAccountById(id);

    }

    @PostMapping("/bankAccount{id}/addMoney")
    public ResponseEntity<String> addMoney(@PathVariable int id, @RequestParam BigDecimal amount) {
        accountService.addMoney(id, amount);
        return ResponseEntity.ok("Счет успешно пополнен");
    }

    @GetMapping("/bankAccount/{id}/minusMoney/isAllow")
    public ResponseEntity<Boolean> isAllow(@PathVariable int id, @RequestParam("amount") BigDecimal amount) {
        return accountService.isAllow(id, amount);
    }

    @PostMapping("/bankAccount/{id}/minusMoney")
    public ResponseEntity<String> minusMoney(@PathVariable int id, @RequestParam("amount") BigDecimal amount) {
        return accountService.minusMoney(id, amount);
    }
}
