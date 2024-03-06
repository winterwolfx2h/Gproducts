package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductResourceABE.LogicalResource;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.Bcm.Model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Table(name = "ProductOffering")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductOffering extends Product {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "shdes", nullable = false)
    private String shdes;

    @Column(name = "parent", nullable = true)
    private String parent;

    @Column(name = "externalLinkId", nullable = true)
    private String externalLinkId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "po_SpecCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductSpecification productSpecification;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "poAttribute_code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private POAttributes poAttributes;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "poRelation_Code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductRelation productRelation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PoOfferRelation_Code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductOfferRelation productOfferRelation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "logResourceId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LogicalResource logicalResource;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "phyResourceId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PhysicalResource physicalResource;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "businessProcessId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BusinessProcess businessProcess;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "eligibilityId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Eligibility eligibility;

}
