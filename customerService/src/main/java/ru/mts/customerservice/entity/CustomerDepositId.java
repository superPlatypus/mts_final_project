package ru.mts.customerservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CustomerDepositId implements Serializable {
    @Column(name = "customer_id")
    private int customer_id;
    @Column(name = "deposit_id")
    private int deposit_id;
}