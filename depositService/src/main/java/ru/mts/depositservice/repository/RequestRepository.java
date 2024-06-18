package ru.mts.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.depositservice.entity.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
     Optional<Request> findByDepositId(int depositId);
     void deleteByDepositId(int depositId);
     List<Request> findAllByCustomerIdAndRequestStatusId(int customerId, int requestStatusId);
}
