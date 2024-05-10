package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "ProductOffering")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductOffering extends Product {

    @Column(name = "poType", nullable = false)
    private String poType;

    //Might Delete Later
    @Column(name = "externalId", nullable = true)
    private String externalId;

    @Column(name = "parent", nullable = true)
    private String parent;

    @Column(name = "sellIndicator", nullable = true)
    private boolean sellIndicator;

    @Column(name = "category", nullable = true)
    private String category;

    @Column(name = "BS_externalId", nullable = true)
    private String BS_externalId;

    @Column(name = "CS_externalId", nullable = true)
    private String CS_externalId;

    @Column(name = "businessProcess", nullable = true)
    private String businessProcess;

    @ElementCollection
    @CollectionTable(name = "product_offering_channels", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "channel", nullable = true)
    private List<String> channels;


    @Pattern(regexp = "^(PO_PLAN|PO_CHILD)$", message = "invalid code")
    @Column(name = "poParent_Child", nullable = true)
    private String poParent_Child;

    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "physicalResource", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PhysicalResource physicalResource;


    @ElementCollection
    @CollectionTable(name = "product_offering_CFSS", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "serviceSpecType", nullable = true)
    private List<String> customerFacingServiceSpec;


    @ElementCollection
    @CollectionTable(name = "product_offering_markets", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "name")
    private List<String> markets;

    @ElementCollection
    @CollectionTable(name = "product_offering_submarkets", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "name")
    private List<String> submarkets;


    public enum poParent_Child {

        PO_Parent,
        PO_Child
    }

//    public void setPoParent_Child(String poParent_Child) {
////        if (!"PO-Parent".equals(poParent_Child) && !"PO-Child".equals(poParent_Child)) {
////            throw new IllegalArgumentException("Invalid value for poParent_Child. Allowed values are 'PO-Parent' or 'PO-Child'.");
////        }
//        this.poParent_Child = poParent_Child;
//    }
}
