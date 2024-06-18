package ru.mts.depositservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDate requestDate;

    @Column(name = "deposits_id")
    private Integer depositId;

    @Column(name = "request_status_id")
    private Integer requestStatusId;

    @Column(name = "change_datetime")
    private OffsetDateTime changeDateTime;

    public Request(Integer customerId, Integer depositId) {
        this.customerId = customerId;
        requestDate = LocalDate.now();
        this.depositId = depositId;
        requestStatusId = 1;
        changeDateTime = OffsetDateTime.now();
    }
}
