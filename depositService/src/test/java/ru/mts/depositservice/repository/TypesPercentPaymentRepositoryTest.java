package ru.mts.depositservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.depositservice.AbstractIntegrationTest;

public class TypesPercentPaymentRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private TypesPercentPaymentRepository typesPercentPaymentRepository;

    @Test
    @Transactional
    void findAllRepoTest(){
        Assertions.assertEquals(3, typesPercentPaymentRepository.findAll().size());
    }
}
