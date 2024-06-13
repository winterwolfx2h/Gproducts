package com.Bcm.Model.ProductOfferingABE;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ProductOfferRelation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOfferRelation {

  @EmbeddedId private PrimeryKeyProductRelation id;

  @Column(name = "type")
  private String type;
}
