package com.Bcm.Model.Product;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Product")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_id", nullable = false)
    private int Product_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "effectiveFrom", nullable = false)
    private Date effectiveFrom;

    @Column(name = "effectiveTo", nullable = false)
    private Date effectiveTo;

    @Column(name = "description", nullable = false)
    private String description;


    @Column(name = "poType", nullable = false)
    private String poType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "po_FamilyCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Family family;

    @Column(name = "subFamily", nullable = false)
    private String subFamily;

    public Product convertToProduct() {
        Product product = new Product();
        product.setProduct_id(this.getProduct_id());
        product.setName(this.getName());
        product.setEffectiveFrom(this.getEffectiveFrom());
        product.setEffectiveTo(this.getEffectiveTo());
        product.setDescription(this.getDescription());
        product.setPoType(this.getPoType());
        product.setFamily(this.getFamily());
        product.setSubFamily(this.getSubFamily());

        return product;
    }
}