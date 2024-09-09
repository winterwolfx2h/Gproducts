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
public class ProductDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Product_id", nullable = false)
  private int Product_id;

  @Column(name = "productType", nullable = true)
  private String productType;

  @Column(name = "name", nullable = false)
  private String name;

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

  @Column(name = "family_name")
  private String familyName;

  @Column(name = "subFamily")
  private String subFamily;

  @Column(name = "sellInd")
  private Boolean sellInd;

  @Column(name = "quantity_Ind")
  private Boolean quantityInd;

  @Column(name = "stockInd")
  private Boolean stockInd;

  @Column(name = "externalId")
  private String externalId;

  @Column(name = "externalCode", nullable = true)
  private String externalCode;

  @Column(name = "status", nullable = false)
  private String status;
}
