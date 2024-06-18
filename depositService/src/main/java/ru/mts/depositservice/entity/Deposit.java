package ru.mts.depositservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "deposits")
public class Deposit {

    //проценты
    public Deposit(int depositAccountId, int depositTypeId, BigDecimal depositAmount, int typePercentPaymentId, int month) {
        this.depositAccountId = depositAccountId;
        this.depositTypeId = depositTypeId;
        this.depositAmount = depositAmount;
        this.typePercentPaymentId = typePercentPaymentId;


        depositRefill = depositTypeId == 1 || depositTypeId == 2;
        startDate = LocalDate.now();
        endDate = startDate.plusMonths(month);
        depositRate = BigDecimal.valueOf(16.20);
        percentPaymentAccountId = depositAccountId;
        if (typePercentPaymentId == 1){
            percentPaymentDate = startDate.plusMonths(1);
        }
        else {
            percentPaymentDate = endDate;
        }
        depositRefundAccountId = depositAccountId;
    }
    //капитализация
    public Deposit(int depositAccountId, int depositTypeId, BigDecimal depositAmount, int month) {
        this.depositAccountId = depositAccountId;
        this.depositTypeId = depositTypeId;
        this.depositAmount = depositAmount;

        depositRefill = depositTypeId == 1 || depositTypeId == 2;
        startDate = LocalDate.now();
        endDate = startDate.plusMonths(month);
        depositRate = BigDecimal.valueOf(16.2);
        capitalization = true;
        typePercentPaymentId = 3;
        depositRefundAccountId = depositAccountId;
    }

    @Id
    @Column(name = "id_deposit")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "deposit_account_id")
    private int depositAccountId;

    @Column(name = "deposits_type_id")
    private int depositTypeId;

    @Column(name = "deposit_refill")
    private boolean depositRefill;

    @Column(name = "deposits_amount")
    private BigDecimal depositAmount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "deposit_rate")
    private BigDecimal  depositRate;

    @Column(name = "type_percent_payment_id")
    private int typePercentPaymentId;

    @Column(name = "percent_payment_account_id")
    private int percentPaymentAccountId;

    @Column(name = "percent_payment_date")
    private LocalDate percentPaymentDate;

    @Column(name = "capitalization")
    private boolean capitalization;

    @Column(name = "deposit_refund_account_id")
    private int depositRefundAccountId;


}
