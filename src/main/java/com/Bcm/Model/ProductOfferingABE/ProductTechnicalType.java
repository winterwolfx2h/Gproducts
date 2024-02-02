package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "ProductTechnicalType")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductTechnicalType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int po_ProdTechTypeCode;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "productSpecification_id")
    private ProductSpecification productSpecification;
}
