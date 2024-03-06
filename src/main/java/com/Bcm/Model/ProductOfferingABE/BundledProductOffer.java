package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "BundledProductOffer")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BundledProductOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bdo_Code", nullable = false)
    private int bdo_Code;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "validFor", nullable = false)
    private String validFor;


}

