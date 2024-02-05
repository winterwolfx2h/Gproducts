package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.DefaultProductOfferNumber;
import com.Bcm.Model.ProductOfferingABE.SubClasses.NumberRelationOfferLowerLimit;
import com.Bcm.Model.ProductOfferingABE.SubClasses.NumberRelationOfferUpperLimit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "BundledProductOfferOption")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BundledProductOfferOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int bdoo_Code;

    @Column(name = "defaultProductOfferNumber", nullable = false)
    @OneToMany(mappedBy = "bundledProductOfferOption")
    private List<DefaultProductOfferNumber> defaultProductOfferNumber;

    @Column(name = "numberRelationOfferLowerLimit", nullable = false)
    @OneToMany(mappedBy = "bundledProductOfferOption")
    private List<NumberRelationOfferLowerLimit> numberRelationOfferLowerLimit;

    @Column(name = "numberRelationOfferUpperLimit", nullable = false)
    @OneToMany(mappedBy = "bundledProductOfferOption")
    private List<NumberRelationOfferUpperLimit> numberRelationOfferUpperLimit;

}

