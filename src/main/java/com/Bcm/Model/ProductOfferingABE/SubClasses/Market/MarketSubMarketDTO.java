package com.Bcm.Model.ProductOfferingABE.SubClasses.Market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarketSubMarketDTO {
  private int lr_id;
  private String service_name;
  private int service_id;
  private int market_code;
  private String sub_market_name;
  private String market_name;
  private int sub_market_code;

  public MarketSubMarketDTO(
      int lr_id,
      String service_name,
      int service_id,
      int market_code,
      String market_name,
      int sub_market_code,
      String sub_market_name) {
    this.lr_id = lr_id;
    this.service_name = service_name;
    this.service_id = service_id;
    this.market_code = market_code;
    this.market_name = market_name;
    this.sub_market_code = sub_market_code;
    this.sub_market_name = sub_market_name;
  }
}
