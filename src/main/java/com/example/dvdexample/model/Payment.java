package com.example.dvdexample.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Payment {
    @Id
    @Column(name = "payment_id")
    private Integer id;
    private BigDecimal amount;
    private Timestamp paymentDate;
    @OneToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;
}
