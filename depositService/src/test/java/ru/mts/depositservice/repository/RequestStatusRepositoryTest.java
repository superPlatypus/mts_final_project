package ru.mts.depositservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mts.depositservice.AbstractIntegrationTest;

public class RequestStatusRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private RequestStatusRepository requestStatusRepository;

    @Test
    void getRequestStatusTest() {
        Assertions.assertEquals(4, requestStatusRepository.findAll().size());
    }
}
