package com.example.dvdexample.bussiness;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ProfitReport {
    private int filmId;
    private Date paymentDate;
    private BigDecimal amount;
}
