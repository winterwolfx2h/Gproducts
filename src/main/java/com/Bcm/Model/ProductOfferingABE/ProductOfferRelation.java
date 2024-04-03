package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "validFor", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date validFor;

    @Column(name = "length", nullable = false)
    private String length;


}

