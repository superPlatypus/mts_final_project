package ru.mts.accountservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.accountservice.repository.BankAccountRepository;

@RestController
public class BankAccountController {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("/")
    public String getBankAccountById() {
        return bankAccountRepository.findById(1).get().toString();
    }
}