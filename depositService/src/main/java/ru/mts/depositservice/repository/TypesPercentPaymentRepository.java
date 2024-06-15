package ru.mts.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.depositservice.entity.TypesPercentPayment;
@Repository
public interface TypesPercentPaymentRepository  extends JpaRepository<TypesPercentPayment, Integer> {
}
