package com.Bcm.Model.Product;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date effectiveFrom;

    @Column(name = "effectiveTo", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date effectiveTo;

    @Column(name = "description")
    private String description;

    @Column(name = "detailedDescription")
    private String detailedDescription;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "subFamily")
    private String subFamily;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = POAttributes.class)
    @JoinColumn(name = "Product_id")
    @JsonIgnore
    private List<POAttributes> poAttributes;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductPrice.class)
    @JoinColumn(name = "Product_id")
    @JsonIgnore
    private List<ProductPrice> productPrices;

    public Product convertToProduct() {
        Product product = new Product();
        product.setProduct_id(this.getProduct_id());
        product.setName(this.getName());
        product.setEffectiveFrom(this.getEffectiveFrom());
        product.setEffectiveTo(this.getEffectiveTo());
        product.setDescription(this.getDescription());
        product.setDetailedDescription(this.getDetailedDescription());
        product.setFamilyName(this.getFamilyName());
        product.setSubFamily(this.getSubFamily());

        return product;
    }
}
