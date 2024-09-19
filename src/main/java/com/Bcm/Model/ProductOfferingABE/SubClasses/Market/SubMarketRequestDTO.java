package com.Bcm.Model.ProductOfferingABE.SubClasses.Market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubMarketRequestDTO {
  private Integer po_SubMarketCode;
  private String subMarketName;
  private String subMarketDescription;
}
