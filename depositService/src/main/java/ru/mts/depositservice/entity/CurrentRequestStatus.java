package ru.mts.depositservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

//@Data
//@Entity
//@Table(name = "current_request_status")
//public class CurrentRequestStatus {
//    @EmbeddedId
//    private CurrentRequestStatusId id;
//
//    @ManyToOne
//    @MapsId("requestId")
//    @JoinColumn(name = "request_id")
//    private Request request;
//
//    @ManyToOne
//    @MapsId("requestStatusId")
//    @JoinColumn(name = "request_status_id")
//    private RequestStatus requestStatus;
//
//    @Column(name = "change_datetime", nullable = false)
//    private Date changeDatetime;
//}