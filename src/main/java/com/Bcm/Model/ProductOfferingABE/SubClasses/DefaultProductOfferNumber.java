package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.Bcm.Model.ProductOfferingABE.BundledProductOfferOption;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "DefaultProductOfferNumber")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DefaultProductOfferNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int pos_code;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bundledProductOfferOption_id")
    private BundledProductOfferOption bundledProductOfferOption;
}