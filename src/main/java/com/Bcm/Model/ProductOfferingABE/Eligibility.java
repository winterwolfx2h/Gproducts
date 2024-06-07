package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
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
            name = "eligibility_entity",
            joinColumns = @JoinColumn(name = "eligibility_id"),
            inverseJoinColumns = @JoinColumn(name = "entityCode"))
    Set<EligibilityEntity> entities;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "eligibility_channel",
            joinColumns = @JoinColumn(name = "eligibility_id"),
            inverseJoinColumns = @JoinColumn(name = "po_ChannelCode"))
    Set<Channel> channels;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eligibility_id", nullable = false)
    private int eligibility_id;
    @Column(name = "stock_Indicator")
    private Boolean stock_Indicator;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOffering.class)
    @JoinColumn(name = "eligibility_id")
    @JsonIgnore
    private List<ProductOffering> productOfferings;


}
