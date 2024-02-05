package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.Bcm.Model.ProductOfferingABE.BundledProductOffer;
import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelationship;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ValidFor")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValidFor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int povf_code;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bundledProductOffer_id")
    private BundledProductOffer bundledProductOffer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "productOfferingRelationship_id")
    private ProductOfferingRelationship productOfferingRelationship;
}