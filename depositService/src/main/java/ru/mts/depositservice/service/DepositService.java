package ru.mts.depositservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.depositservice.entity.Deposit;
import ru.mts.depositservice.repository.DepositRepository;
import ru.mts.depositservice.repository.RequestRepository;

import java.math.BigDecimal;

@Service
public class DepositService {
    private final DepositRepository depositRepository;
    private final RequestRepository requestRepository;

    public DepositService(DepositRepository depositRepository, RequestRepository requestRepository) {
        this.depositRepository = depositRepository;
        this.requestRepository = requestRepository;
    }

    @Transactional
    public void addMoney(int depositId, BigDecimal amount) {
        Deposit deposit = depositRepository.findById(depositId).orElseThrow(() -> new RuntimeException("Вклад не найден"));
        BigDecimal depositAmount = deposit.getDepositAmount();
        depositAmount = depositAmount.add(amount);
        deposit.setDepositAmount(depositAmount);
    }

    @Transactional
    public void deleteDeposit(int id) {
        requestRepository.deleteByDepositId(id);
        depositRepository.deleteById(id);
    }
}
