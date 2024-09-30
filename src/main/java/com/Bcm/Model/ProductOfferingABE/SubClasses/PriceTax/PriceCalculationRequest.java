package com.Bcm.Model.ProductOfferingABE.SubClasses.PriceTax;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceCalculationRequest {
  @Schema(description = "Product Price Code", example = "101")
  private int productPriceCode;

  @Schema(description = "List of Tax Codes", example = "[1, 2, 3]")
  private List<Integer> taxCodes;
}
