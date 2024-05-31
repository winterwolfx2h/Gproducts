package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "ProductOfferRelation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOfferRelation {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pOfferRelationCode", nullable = false)
    private int pOfferRelationCode;


    @Column(name = "Product_id",nullable = false)
    private int Product_id;
    @ManyToOne
    @JoinColumn(name = "related_product_id", referencedColumnName = "Product_id")
    private Product relatedProduct;

    @Column(name = "type", nullable = true)
    private String type;
}
