package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.Bcm.Model.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "Entity")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EligibilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_seq_generator")
    @SequenceGenerator(name = "entity_seq_generator", sequenceName = "entity_sequence", allocationSize = 1)
    @Column(name = "entityCode")
    private int entityCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Product.class)
    @JoinColumn(name = "entityCode")
    @JsonIgnore
    private List<Product> products;
}
