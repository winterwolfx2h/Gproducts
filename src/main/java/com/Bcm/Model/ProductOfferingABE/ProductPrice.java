package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @Column(name = "oneTimeAmount", nullable = true)
  private int oneTimeAmount;

  @Column(name = "cashPrice", nullable = true)
  private float cashPrice;

  @Column(name = "recuringAmount", nullable = true)
  private float recuringAmount;

  @Column(name = "validFrom", nullable = true)
  private Date validFrom;

  @Column(name = "priceVersion", nullable = true)
  private int priceVersion;

  @Column(name = "Product_id", nullable = false)
  private int Product_id;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = Product.class)
  @JoinColumn(name = "entityCode")
  @JsonIgnore
  private List<Product> products;
}
