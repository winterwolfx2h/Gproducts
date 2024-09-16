package com.Bcm.Model.ProductOfferingABE.SubClasses.Market;

import com.Bcm.Model.ProductResourceABE.LogicalResource;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SubMarket")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties("subMarkets")
public class SubMarket {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subMarket_seq_generator")
  @SequenceGenerator(name = "subMarket_seq_generator", sequenceName = "subMarket_sequence", allocationSize = 1)
  @Column(name = "po_SubMarketCode", nullable = false)
  private int po_SubMarketCode;

  @Column(name = "subMarketName", nullable = false)
  private String subMarketName;

  @Column(name = "subMarketDescription", nullable = false)
  private String subMarketDescription;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "marketCode", nullable = true)
  private Market market;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = LogicalResource.class)
  @JoinColumn(name = "po_SubMarketCode")
  @JsonIgnore
  private List<LogicalResource> logicalResources;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = PhysicalResource.class)
  @JoinColumn(name = "po_SubMarketCode")
  @JsonIgnore
  private List<PhysicalResource> physicalResources;
}
