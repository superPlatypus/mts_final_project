package ru.mts.customerservice.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id_customer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_customer;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "bank_account_id")
    private int bank_account_id;
}
