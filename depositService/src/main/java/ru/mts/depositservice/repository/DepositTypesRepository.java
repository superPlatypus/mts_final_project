package ru.mts.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.depositservice.entity.DepositTypes;

@Repository
public interface DepositTypesRepository extends JpaRepository<DepositTypes, Integer> {
}
