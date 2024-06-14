package ru.mts.depositservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request")
    private Integer id;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "request_date")
    private Date requestDate;

    @ManyToOne
    @JoinColumn(name = "deposits_id")
    private Deposit deposit;
}
