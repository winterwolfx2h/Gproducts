package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationResponse {
  private Integer productId;
  private String productName;
  private String familyName;
  private String subFamily;
  private Integer relatedProductId;
  private String type;
  private String subType;
  private String markets;
  private String submarkets;
  @JsonIgnore private String message;

  public RelationResponse(
      Integer productId,
      String productName,
      String familyName,
      String subFamily,
      Integer relatedProductId,
      String type,
      String subType,
      String markets,
      String submarkets) {
    this.productId = productId;
    this.productName = productName;
    this.familyName = familyName;
    this.subFamily = subFamily;
    this.relatedProductId = relatedProductId;
    this.type = type;
    this.subType = subType;
    this.markets = markets;
    this.submarkets = submarkets;
  }

  public RelationResponse(Integer productId, String productName) {
    this.productId = productId;
    this.productName = productName;
  }

  public RelationResponse(Integer productId, String productName, Integer relatedProductId, String type) {
    this.productId = productId;
    this.productName = productName;
    this.relatedProductId = relatedProductId;
    this.type = type;
  }

  public RelationResponse(String message) {
    this.message = message;
  }
}
