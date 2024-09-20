package com.Bcm.Model.Product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ProductOfferingDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Product_id", nullable = false)
  private int Product_id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "poType", nullable = false)
  private String poType;

  @Column(name = "effectiveFrom", nullable = false)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date effectiveFrom;

  @Column(name = "effectiveTo", nullable = false)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date effectiveTo;

  @Column(name = "description", nullable = true)
  private String description;

  @Column(name = "detailedDescription", nullable = false)
  private String detailedDescription;

  @Column(name = "family_name", nullable = false)
  private String familyName;

  @Column(name = "subFamily", nullable = false)
  private String subFamily;

  @Column(name = "sellInd")
  private Boolean sellInd;

  @Column(name = "quantity_Ind")
  private Boolean quantityInd;

  @Column(name = "stockInd")
  private Boolean stockInd;

  @Column(name = "externalId")
  private String externalId;

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "market", nullable = true)
  private String markets;

  @Column(name = "submarket", nullable = true)
  private String submarkets;

  @Column(name = "type", nullable = true)
  private String type;

  @Column(name = "global")
  private boolean global;
}
