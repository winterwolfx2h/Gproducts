package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
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

    @Column(name = "poType", nullable = false)
    private String poType;

    @Column(name = "parent", nullable = true)
    private String parent;

    /*@Column(name = "externalLinkId", nullable = true)
    private String externalLinkId;*/

    @Column(name = "parameter", nullable = false)
    private boolean parameter;

    @Column(name = "sellIndicator", nullable = false)
    private boolean sellIndicator;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "BS_externalId", nullable = true)
    private String BS_externalId;

    @Column(name = "CS_externalId", nullable = true)
    private String CS_externalId;

    @Column(name = "businessProcess", nullable = false)
    private String businessProcess;

    @ElementCollection
    @CollectionTable(name = "product_offering_channels", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "channel")
    private List<String> channels;

    @Column(name = "poParent_Child", nullable = false)
    private String poParent_Child = "PO-Parent";
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "physicalResource", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PhysicalResource physicalResource;
    @ElementCollection
    @CollectionTable(name = "product_offering_CFSS", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "serviceSpecType")
    private List<String> customerFacingServiceSpec;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_offering_markets",
            joinColumns = @JoinColumn(name = "Product_id"),
            inverseJoinColumns = @JoinColumn(name = "po_MarketCode")
    )
    private List<Market> markets;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_offering_submarkets",
            joinColumns = @JoinColumn(name = "Product_id"),
            inverseJoinColumns = @JoinColumn(name = "po_SubMarketCode")
    )
    private List<SubMarket> submarkets;

    public void setPoParent_Child(String poParent_Child) {
        if (!"PO-Parent".equals(poParent_Child) && !"PO-Child".equals(poParent_Child)) {
            throw new IllegalArgumentException("Invalid value for poParent_Child. Allowed values are 'PO-Parent' or 'PO-Child'.");
        }
        this.poParent_Child = poParent_Child;
    }

/*
    // Making the Market and SubMarket an array of objects that interacts with the Product Offering Controller
            to be eligible for posting more than one instance per product offering payload.


   // UnderDevelopment
    @ElementCollection
    @CollectionTable(name = "product_offering_submarkets", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "name")
    private List<String> markets;

   // UnderDevelopment
    @ElementCollection
    @CollectionTable(name = "product_offering_CFSS", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "name")
    private List<String> submarkets;
    */

}
