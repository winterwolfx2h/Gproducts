package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "ProductOffering")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOffering extends Product {

  @Column(name = "poType", nullable = false)
  private String poType;

  @Column(name = "parent")
  private String parent;

  @Column(name = "workingStep")
  private String workingStep;

  @Column(name = "category")
  private String category;

  @Column(name = "BS_externalId")
  private String BS_externalId;

  @Column(name = "CS_externalId")
  private String CS_externalId;

  @Pattern(regexp = "^(PO-PARENT|PO-CHILD)$", message = "invalid code")
  @Column(name = "poParent_Child")
  private String poParent_Child;

  @Column(name = "markets", nullable = true)
  private String markets;

  @Column(name = "submarkets", nullable = true)
  private String submarkets;

  @Column(name = "businessProcess_id")
  private Integer businessProcess_id;

  @Column(name = "pr_id")
  private Integer pr_id;

  @Column(name = "global")
  private boolean global;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOfferRelation.class)
  @JoinColumn(name = "Product_id")
  @JsonIgnore
  private List<ProductOfferRelation> productOfferRelations;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = BusinessProcess.class)
    @JoinColumn(name = "Product_id")
    @JsonIgnore
    private BusinessProcess businessProcesses;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = BusinessProcess.class)
  @JoinColumn(name = "Product_id")
  @JsonIgnore
  private List<BusinessProcess> businessProcesses;
}
