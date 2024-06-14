package ru.mts.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.depositservice.entity.RequestStatus;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Integer> {
}
