package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Table(name = "ProductSpecification")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int po_SpecCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "family", nullable = false)
    @OneToMany(mappedBy = "productSpecification")
    private List<Family> family;

    @Column(name = "category", nullable = false)
    @OneToMany(mappedBy = "productSpecification")
    private List<Category> category;

    @Column(name = "productType", nullable = false)
    @OneToMany(mappedBy = "productSpecification")
    private List<ProductType> productType;

    @Column(name = "market", nullable = false)
    @OneToMany(mappedBy = "productSpecification")
    private List<Market> market;

    @Column(name = "productSubType", nullable = false)
    @OneToMany(mappedBy = "productSpecification")
    private List<ProductSubType> productSubType;

    @Column(name = "productTechnicalType", nullable = false)
    @OneToMany(mappedBy = "productSpecification")
    private List<ProductTechnicalType> productTechnicalType;

    @Column(name = "timeZoneType", nullable = false)
    @OneToMany(mappedBy = "productSpecification")
    private List<TimeZoneType> timeZoneType;

    @Column(name = "ratePlan", nullable = false)
    @OneToMany(mappedBy = "productSpecification")
    private List<RatePlan> ratePlan;

    @Column(name = "dateFinEngagement", nullable = false)
    @OneToMany(mappedBy = "productSpecification")
    private List<DateFinEngagement> dateFinEngagement;

    @Column(name = "dureeEngagement", nullable = false)
    @OneToMany(mappedBy = "productSpecification")
    private List<DureeEngagement> dureeEngagement;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "productOffering_id")
    private ProductOffering productOffering;
}