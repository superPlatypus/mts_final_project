package ru.mts.accountservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.mts.accountservice.entity.BankAccount;
import ru.mts.accountservice.repository.BankAccountRepository;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final BankAccountRepository bankAccountRepository;

    public AccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }


    public Integer getCurrentBankAccount() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public BankAccount getBankAccountById(int id) {
        return bankAccountRepository.findById(id).get();
    }

    public void addMoney(int id, BigDecimal amount) {
        BankAccount bankAccount = bankAccountRepository.findById(id).get();
        BigDecimal currentAmount = bankAccount.getAmount();
        bankAccount.setAmount(currentAmount.add(amount));
        bankAccountRepository.save(bankAccount);
    }

    public ResponseEntity<String> minusMoney(int id, BigDecimal amount) {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("Счет не найден"));
        if (bankAccount.getAmount().compareTo(amount) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Недостаточно средств");
        }
        BigDecimal currentAmount = bankAccount.getAmount();
        bankAccount.setAmount(currentAmount.subtract(amount));
        bankAccountRepository.save(bankAccount);
        return ResponseEntity.ok("Деньги успешно списаны");
    }

    public ResponseEntity<Boolean> isAllow(int id, BigDecimal amount) {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("Счет не найден"));

        if (bankAccount.getAmount().compareTo(amount) < 0) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
        else{
            return ResponseEntity.ok(true);
        }
    }
}
