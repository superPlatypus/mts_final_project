package ru.mts.depositservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "request_statuses")
public class RequestStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request_status")
    private Integer id;

    @Column(name = "request_status_name")
    private String name;

}