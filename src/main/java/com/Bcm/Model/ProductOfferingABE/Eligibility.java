package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Eligibility")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Eligibility {

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "eligibility_pricegroup",
            joinColumns = @JoinColumn(name = "eligibility_id"),
            inverseJoinColumns = @JoinColumn(name = "productPriceGroupCode"))
    Set<ProductPriceGroup> productPriceGroups;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eligibility_id", nullable = false)
    private int eligibility_id;

    //    @ManyToMany
//    @JsonIgnore
//    @JoinTable(
//            name = "eligibility_entity",
//            joinColumns = @JoinColumn(name = "eligibility_id"),
//            inverseJoinColumns = @JoinColumn(name = "entityCode"))
//    Set<EligibilityEntity> entities;
//
//    @ManyToMany
//    @JsonIgnore
//    @JoinTable(
//            name = "eligibility_channel",
//            joinColumns = @JoinColumn(name = "eligibility_id"),
//            inverseJoinColumns = @JoinColumn(name = "po_ChannelCode"))
//    Set<Channel> channels;
    @Column(name = "stock_Indicator")
    private Boolean stock_Indicator;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOffering.class)
    @JoinColumn(name = "eligibility_id")
    @JsonIgnore
    private List<ProductOffering> productOfferings;

    @Column(name = "Product_id", nullable = false)
    private int Product_id;

}
