package ru.mts.accountservice.entity;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bank_account")
    private int id;

    @Column(name = "num_bank_account")
    private BigDecimal numBankAccount;

    @Column(name = "amount")
    private BigDecimal amount;
}
