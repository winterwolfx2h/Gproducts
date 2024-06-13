package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ProductPriceGroup")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductPriceGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productPriceGroup_seq_generator")
  @SequenceGenerator(
      name = "productPriceGroup_seq_generator",
      sequenceName = "productPriceGroup_sequence",
      allocationSize = 1)
  @Column(name = "productPriceGroupCode", nullable = false)
  private int productPriceGroupCode;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = true)
  private String description;

  //    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOffering.class)
  //    @JsonIgnore
  //    @JoinTable(
  //            name = "ProductOffering_pricegroup",
  //            joinColumns = @JoinColumn(name = "productPriceGroupCode"),
  //            inverseJoinColumns = @JoinColumn(name = "Product_id"))
  //    Set<ProductOffering> productOfferings;

  @JsonIgnore
  @ManyToMany(mappedBy = "productPriceGroups")
  private Set<Product> products = new HashSet<>();
}
