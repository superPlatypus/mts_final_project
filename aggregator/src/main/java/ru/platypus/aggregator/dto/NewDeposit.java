package ru.platypus.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewDeposit {
    private String phoneNumber;
    private int depositTypeId;
    private BigDecimal depositAmount;
    private int typePercentPaymentId;
    private int month;
    private boolean capitalization;
}
