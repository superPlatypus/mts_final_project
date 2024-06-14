package ru.mts.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.depositservice.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {
}
