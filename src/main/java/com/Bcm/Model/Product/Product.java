package com.Bcm.Model.Product;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Model.ProductOfferingABE.ProductRelation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "detailedDescription", nullable = true)
    private String detailedDescription;

    @Column(name = "family_name", nullable = false)
    private String familyName;

    @Column(name = "subFamily", nullable = false)
    private String subFamily;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "poRelation_Code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductRelation productRelation;

    @OneToMany(fetch = FetchType.EAGER)
    private List<POAttributes> poAttributes;

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
