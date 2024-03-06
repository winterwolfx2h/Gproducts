package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "validFor", nullable = false)
    private Date validFor;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "numberRelationOfferLowerLimit", nullable = false)
    private String numberRelationOfferLowerLimit;

    @Column(name = "numberRelationOfferUpperLimit", nullable = false)
    private String numberRelationOfferUpperLimit;

}

