package com.Bcm.Model.ProductResourceABE;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PhysicalResource")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhysicalResource {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PR_id ", nullable = false)
  private int PR_id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "physicalResourceType", nullable = false)
  private String physicalResourceType;

  @Column(name = "physicalResourceFormat", nullable = false)
  private String physicalResourceFormat;

  @Column(name = "generationType", nullable = false)
  private String generationType;

  @Column(name = "externalNPCode", nullable = true)
  private String externalNPCode;

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "po_MarketCode", insertable = true, updatable = true)
  private Integer po_MarketCode;

  @Column(name = "po_SubMarketCode", insertable = true, updatable = true)
  private Integer po_SubMarketCode;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOffering.class)
  @JoinColumn(name = "PR_id")
  @JsonIgnore
  private List<ProductOffering> productOfferings;
}
