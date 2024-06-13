package com.Bcm.Model.ProductOfferingABE.SubClasses;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "SubMarket")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubMarket {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "submarket_seq_generator")
  @SequenceGenerator(name = "submarket_seq_generator", sequenceName = "submarket_sequence", allocationSize = 1)
  @Column(name = "po_SubMarketCode", nullable = false)
  private int po_SubMarketCode;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = true)
  private String description;
}
