package com.Bcm.Controller.ProductController;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsResponse {
  private Integer channelCode;
  private String channelName;
  private Integer entityCode;
  private String entityName;
  private Integer productPriceGroupCode;
  private String productPriceGroupName;
  private Boolean stockIndicator;
}
