package ru.mts.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.accountservice.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
}
