package com.Bcm.Model.ProductOfferingABE.SubClasses.Market;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketRequestDTOUpdate {
  private String name;
  private String description;
  private List<SubMarketRequestDTO> subMarkets;
}
