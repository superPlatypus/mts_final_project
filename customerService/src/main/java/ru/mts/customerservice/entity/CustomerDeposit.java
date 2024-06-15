package ru.mts.customerservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_deposits")
public class CustomerDeposit {

    @EmbeddedId
    private CustomerDepositId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("customer_id")
    @JoinColumn(name = "customer_id")
    private Customer customer;
}