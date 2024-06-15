package ru.mts.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.depositservice.entity.CurrentRequestStatus;
@Repository
public interface CurrentRequestStatusRepository extends JpaRepository<CurrentRequestStatus, Integer> {
}
