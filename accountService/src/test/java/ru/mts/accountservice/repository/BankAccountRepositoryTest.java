package ru.mts.accountservice.repository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mts.accountservice.AbstractIntegrationTest;

public class BankAccountRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private BankAccountRepository accountRepository;

    @Test
    void findAllRepoTest(){
        System.out.println(accountRepository.findAll());
        Assertions.assertEquals(2, accountRepository.findAll().size());
    }
}
