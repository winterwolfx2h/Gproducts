package com.Bcm.Model.Product;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Model.ProductOfferingABE.ProductPriceGroup;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import com.Bcm.Model.ProductOfferingABE.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Type.class)
    @JoinColumn(name = "Product_id")
    @JsonIgnore
    private List<Type> types;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "Product_entity",
            joinColumns = @JoinColumn(name = "Product_id"),
            inverseJoinColumns = @JoinColumn(name = "entityCode"))
    private Set<EligibilityEntity> entityCode = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "Product_channel",
            joinColumns = @JoinColumn(name = "Product_id"),
            inverseJoinColumns = @JoinColumn(name = "channelCode"))
    private Set<Channel> channelCode = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "Product_pricegroup",
            joinColumns = @JoinColumn(name = "Product_id"),
            inverseJoinColumns = @JoinColumn(name = "productPriceGroupCode"))
    private Set<ProductPriceGroup> productPriceGroups = new HashSet<>();

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
