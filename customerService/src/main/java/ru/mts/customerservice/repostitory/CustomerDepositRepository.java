package ru.mts.customerservice.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.customerservice.entity.CustomerDeposit;
import ru.mts.customerservice.entity.CustomerDepositId;

import java.util.List;

public interface CustomerDepositRepository extends JpaRepository<CustomerDeposit, CustomerDepositId> {

    List<CustomerDeposit> findByCustomer_IdCustomer(int customerId);
}
