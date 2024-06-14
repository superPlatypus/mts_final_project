package ru.mts.depositservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TypesPercentPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_percent_payment")
    private Integer id;

    @Column(name = "type_percent_payment_period")
    private String period;
}
