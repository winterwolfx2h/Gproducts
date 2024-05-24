package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ProductOfferRelation")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOfferRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PoOfferRelation_Code", nullable = false)
    private int PoOfferRelation_Code;

    @Column(name = "type", nullable = true)
    private String type;

    /*@ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "Product_id")
    private ProductOffering productOffering;*/
}

