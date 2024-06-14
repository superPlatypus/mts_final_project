package ru.mts.depositservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "deposits_types")
public class DepositTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deposits_types")
    private Integer id;

    @Column(name = "deposits_types_name")
    private String name;
}
