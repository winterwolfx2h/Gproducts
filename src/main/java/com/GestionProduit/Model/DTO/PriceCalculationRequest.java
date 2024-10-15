package com.GestionProduit.Model.DTO;

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
  @Schema(description = "id", example = "101")
  private long id;

  @Schema(description = "List of Tax Codes", example = "[1, 2, 3]")
  private List<Integer> taxCodes;
}
