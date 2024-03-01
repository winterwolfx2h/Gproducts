package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Parent;
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

    @Column(name = "description", nullable = false)
    private String descrption;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "po_ParentCode", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Parent parent;

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
    @JoinColumn(name = "PrResId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductResource productResource;

}
