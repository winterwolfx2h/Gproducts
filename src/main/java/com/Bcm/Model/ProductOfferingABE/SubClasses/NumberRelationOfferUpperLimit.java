package com.Bcm.Model.ProductOfferingABE.SubClasses;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "NumberRelationOfferUpperLimit")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NumberRelationOfferUpperLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int pos_code;

    @Column(name = "name", nullable = false)
    private String name;
    /*

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bundledProductOfferOption_id")
    private BundledProductOfferOption bundledProductOfferOption;
         */
}