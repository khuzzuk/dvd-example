package com.example.dvdexample.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Rental {
    @Id
    @Column(name = "rental_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    @OneToOne(mappedBy = "rental")
    private Payment payment;
}
