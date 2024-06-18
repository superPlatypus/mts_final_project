package ru.mts.accountservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mts.accountservice.AbstractIntegrationTest;

import java.math.BigDecimal;

@SpringBootTest
public class AccountServiceTest extends AbstractIntegrationTest {
    @Autowired
    private AccountService accountService;

    @Test
    void addMoneyTest(){
        BigDecimal amountBefore = accountService.getBankAccountById(1).getAmount();
        BigDecimal amount = BigDecimal.valueOf(15550);
        accountService.addMoney(1, amount);
        BigDecimal amountAfter = accountService.getBankAccountById(1).getAmount();
        Assertions.assertEquals(amountBefore.add(amount), amountAfter);
    }

    @Test
    void minusMoneyTest(){
        BigDecimal amountBefore = accountService.getBankAccountById(1).getAmount();
        BigDecimal amount = BigDecimal.valueOf(15550);
        accountService.minusMoney(1, amount);
        BigDecimal amountAfter = accountService.getBankAccountById(1).getAmount();
        Assertions.assertEquals(amountBefore.subtract(amount), amountAfter);
    }

    @Test
    void isAllowPositiveTest(){
        BigDecimal amount = accountService.getBankAccountById(1).getAmount();
        amount = amount.add(BigDecimal.valueOf(1));
        Assertions.assertFalse(accountService.isAllow(1, amount).getBody());
    }

    @Test
    void isAllowNegativeTest(){
        BigDecimal amount = accountService.getBankAccountById(1).getAmount();
        amount = amount.subtract(BigDecimal.valueOf(1));
        Assertions.assertTrue(accountService.isAllow(1, amount).getBody());
    }

}
