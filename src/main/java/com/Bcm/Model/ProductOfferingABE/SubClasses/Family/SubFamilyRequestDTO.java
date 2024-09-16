package com.Bcm.Model.ProductOfferingABE.SubClasses.Family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubFamilyRequestDTO {
  private Integer po_SubFamilyCode;
  private String subFamilyName;
  private String subFamilyDescription;
}
