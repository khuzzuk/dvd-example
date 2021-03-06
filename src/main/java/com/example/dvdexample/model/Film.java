package com.example.dvdexample.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Film {
    @Id
    @Column(name = "film_id")
    private Integer id;
    private String title;
    @OneToMany(mappedBy = "film")
    private Set<Inventory> inventories;
}
