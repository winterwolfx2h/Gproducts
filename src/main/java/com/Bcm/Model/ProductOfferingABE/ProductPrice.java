package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ProductPrice")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productPrice_seq_generator")
    @SequenceGenerator(name = "productPrice_seq_generator", sequenceName = "productPrice_sequence", allocationSize = 1)
    @Column(name = "productPriceCode", nullable = false)
    private int productPriceCode;

    @Column(name = "amount", nullable = true)
    private int amount;

    @Column(name = "price", nullable = true)
    private float price;

    @Column(name = "recuringPrice", nullable = true)
    private float recuringPrice;

    @Column(name = "Product_id", nullable = false)
    private int Product_id;

}
