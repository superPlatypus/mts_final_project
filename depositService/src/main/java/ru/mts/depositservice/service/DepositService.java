package ru.mts.depositservice.service;

import org.springframework.http.ResponseEntity;
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

    public Deposit getDeposit(int id) {
        return depositRepository.findById(id).orElseThrow(() -> new RuntimeException("Вклад не существует"));

    }

    @Transactional
    public ResponseEntity<Integer> addDepositWithPercents(int accountId, int depositTypeId, BigDecimal depositAmount, int typePercentPaymentId, int month) {
        Deposit deposit = new Deposit(accountId, depositTypeId, depositAmount, typePercentPaymentId, month);
        int id = depositRepository.save(deposit).getId();
        return ResponseEntity.ok(id);
    }

    @Transactional
    public ResponseEntity<Integer> addDepositWithCapitalization(int accountId, int depositTypeId, BigDecimal depositAmount, int month) {
        Deposit deposit = new Deposit(accountId, depositTypeId, depositAmount, month);
        System.out.println(deposit);
        int id = depositRepository.save(deposit).getId();
        return ResponseEntity.ok(id);
    }
}
