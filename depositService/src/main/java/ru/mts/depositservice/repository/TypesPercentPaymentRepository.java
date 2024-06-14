package ru.mts.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.depositservice.entity.TypesPercentPayment;

public interface TypesPercentPaymentRepository  extends JpaRepository<TypesPercentPayment, Integer> {
}
