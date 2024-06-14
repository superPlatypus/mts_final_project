package ru.mts.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.depositservice.entity.CurrentRequestStatus;

public interface CurrentRequestStatusRepository extends JpaRepository<CurrentRequestStatus, Integer> {
}
