package ru.mts.depositservice.entity;


import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class CurrentRequestStatusId implements Serializable {
    private Integer requestId;
    private Integer requestStatusId;
}
