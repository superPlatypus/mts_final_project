package ru.mts.depositservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "deposits")
public class Deposit {
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
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "deposit_rate")
    private double depositRate;

    @Column(name = "type_percent_payment_id")
    private int typePercentPaymentId;

    @Column(name = "percent_payment_account_id")
    private int percentPaymentAccountId;

    @Column(name = "percent_payment_date")
    private Date percentPaymentDate;

    @Column(name = "capitalization")
    private boolean capitalization;

    @Column(name = "deposit_refund_account_id")
    private int depositRefundAccountId;


}
