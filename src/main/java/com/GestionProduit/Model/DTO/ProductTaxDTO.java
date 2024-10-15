package com.GestionProduit.Model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ProductTaxDTO {

  private Integer productId;
  private Integer taxCode;
}
