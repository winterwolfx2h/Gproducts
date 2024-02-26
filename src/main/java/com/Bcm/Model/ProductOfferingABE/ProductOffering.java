package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Category;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Parent;
import com.Bcm.Model.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ProductOffering")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductOffering extends Product {

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "po_ParentCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Parent parent;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "po_CategoryCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "po_SpecCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductSpecification productSpecification;

    @Column(name = "externalId", nullable = false)
    private String externalId;

    @Column(name = "productClass", nullable = false)
    private String productClass;

    @Column(name = "productSubClass", nullable = false)
    private String productSubClass;

}
