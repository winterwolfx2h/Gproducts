package com.Bcm.Model.ProductOfferingABE;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ProductPriceVersion")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductPriceVersion {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productPriceVersion_seq_generator")
  @SequenceGenerator(
      name = "productPriceVersion_seq_generator",
      sequenceName = "productPriceVersion_sequence",
      allocationSize = 1)
  @Column(name = "productPriceVersionCode", nullable = false)
  private int productPriceVersionCode;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = true)
  private String description;
}
