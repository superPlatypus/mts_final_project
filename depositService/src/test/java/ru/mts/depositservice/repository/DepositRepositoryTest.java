package ru.mts.depositservice.repository;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mts.depositservice.AbstractIntegrationTest;
import ru.mts.depositservice.entity.Deposit;

import java.math.BigDecimal;
import java.util.List;

public class DepositRepositoryTest extends AbstractIntegrationTest{
    @Autowired
    private DepositRepository depositRepository;

    @Test
    public void testFindAll() {
        Assertions.assertEquals(List.of(), depositRepository.findAll());
    }
}
