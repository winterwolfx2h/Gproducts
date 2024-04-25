package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductResourceABE.LogicalResource;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Table(name = "ProductOffering")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOffering extends Product {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent", nullable = true)
    private String parent;

    @Column(name = "externalLinkId", nullable = true)
    private String externalLinkId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "po_SpecCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductSpecification productSpecification;

    @OneToMany(fetch = FetchType.EAGER)
    private List<POAttributes> poAttributes;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "poRelation_Code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductRelation productRelation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "PoOfferRelation_Code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductOfferRelation productOfferRelation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "logResourceId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LogicalResource logicalResource;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "phyResourceId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PhysicalResource physicalResource;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "businessProcessId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BusinessProcess businessProcess;

    @ElementCollection
    @CollectionTable(name = "product_offering_eligibility", joinColumns = @JoinColumn(name = "eligibilityId"))
    @Column(name = "channel")
    private List<String> eligibilityChannels;

}
