package com.Bcm.Model.ProductOfferingABE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrimeryKeyProductRelation implements Serializable {

  @Column(name = "relatedProductId", nullable = false)
  private int relatedProductId;

  @Column(name = "product_id", nullable = false)
  private int productId;
}
