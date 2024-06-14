package ru.mts.depositservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.depositservice.entity.Deposit;
import ru.mts.depositservice.entity.DepositTypes;
import ru.mts.depositservice.entity.TypesPercentPayment;
import ru.mts.depositservice.repository.DepositRepository;
import ru.mts.depositservice.repository.DepositTypesRepository;
import ru.mts.depositservice.repository.TypesPercentPaymentRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class DepositController {

    private final DepositTypesRepository depositTypesRepository;
    private final TypesPercentPaymentRepository typesPercentPaymentRepository;
    private final DepositRepository depositRepository;

    public DepositController(DepositTypesRepository depositTypesRepository, TypesPercentPaymentRepository typesPercentPaymentRepository, DepositRepository depositRepository) {
        this.depositTypesRepository = depositTypesRepository;
        this.typesPercentPaymentRepository = typesPercentPaymentRepository;
        this.depositRepository = depositRepository;
    }

    @GetMapping("/")
    public String home() {
        return "Hello from deposit service!!";
    }

    @GetMapping("depositTypes")
    public List<DepositTypes> getDepositTypes() {
        return depositTypesRepository.findAll();
    }

    @GetMapping("deposit")
    public List<Deposit> getDeposit() {
        DepositTypes depositType = depositTypesRepository.findById(1).get();
        TypesPercentPayment typesPercentPayment = typesPercentPaymentRepository.findById(1).get();



        Deposit deposit = new Deposit();
        deposit.setDepositAmount(BigDecimal.valueOf(9500));
        deposit.setDepositRate(15);
        deposit.setDepositAccountId(2);
        deposit.setDepositRefill(true);
        deposit.setDepositRefundAccountId(2);
        deposit.setCapitalization(true);
        deposit.setDepositTypeId(1);
        depositRepository.save(deposit);
        return depositRepository.findAll();
    }
}
