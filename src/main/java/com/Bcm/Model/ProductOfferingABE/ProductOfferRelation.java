package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "ProductOfferRelation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOfferRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productOfferRel_code")
    private Integer productOfferRel_code;

    @Column(name = "relatedProductId", nullable = false)
    private int relatedProductId;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "type")
    private String type;
}
