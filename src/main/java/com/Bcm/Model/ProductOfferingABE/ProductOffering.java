package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

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

    @Column(name = "parent")
    private String parent;

    @Column(name = "workingStep")
    private String workingStep;

    @Column(name = "sellIndicator")
    private Boolean sellIndicator;

    @Column(name = "quantity_Indicator")
    private String quantityIndicator;

    @Column(name = "category")
    private String category;

    @Column(name = "BS_externalId")
    private String BS_externalId;

    @Column(name = "CS_externalId")
    private String CS_externalId;
/*
    @Column(name = "businessProcess", nullable = true)
    private String businessProcess;



    @ElementCollection
    @CollectionTable(name = "product_offering_eligibility", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "eligibilities", nullable = true)
    private List<Integer> eligibility;

 */






    @Pattern(regexp = "^(PO_PARENT|PO_CHILD)$", message = "invalid code")
    @Column(name = "poParent_Child")
    private String poParent_Child;

    /*

    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "logicalResource", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LogicalResource logicalResource;

    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "physicalResource", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PhysicalResource physicalResource;

    @ElementCollection
    @CollectionTable(name = "product_offering_CFSS", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "serviceSpecType", nullable = true)
    private List<String> customerFacingServiceSpec;

     */



    @Column(name = "markets", nullable = true)
    private String markets;


    @Column(name = "submarkets", nullable = true)
    private String submarkets;



    public enum poParent_Child {
        PO_Parent,
        PO_Child
    }
}
