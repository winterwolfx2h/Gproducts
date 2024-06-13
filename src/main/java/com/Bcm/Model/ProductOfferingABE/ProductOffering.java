package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

  @Column(name = "sellIndicator")
  private Boolean sellIndicator;

  @Column(name = "quantity_Indicator")
  private Boolean quantityIndicator;

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

  @JsonIgnore
  @ManyToMany
  @JoinTable(
      name = "ProductOffering_BusinessProcess",
      joinColumns = @JoinColumn(name = "Product_id"),
      inverseJoinColumns = @JoinColumn(name = "businessProcess_id"))
  private Set<BusinessProcess> businessProcess_id = new HashSet<>();

  @JsonIgnore
  @ManyToMany
  @JoinTable(
      name = "ProductOffering_Service",
      joinColumns = @JoinColumn(name = "Product_id"),
      inverseJoinColumns = @JoinColumn(name = "serviceId"))
  private Set<CustomerFacingServiceSpec> serviceId = new HashSet<>();

  @JsonIgnore
  @ManyToMany
  @JoinTable(
      name = "ProductOffering_PhysicalResources",
      joinColumns = @JoinColumn(name = "Product_id"),
      inverseJoinColumns = @JoinColumn(name = "PR_id"))
  private Set<PhysicalResource> PR_id = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOfferRelation.class)
  @JoinColumn(name = "Product_id")
  @JsonIgnore
  private List<ProductOfferRelation> productOfferRelations;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = BusinessProcess.class)
  @JoinColumn(name = "Product_id")
  @JsonIgnore
  private List<BusinessProcess> businessProcesses;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductRelation.class)
  @JoinColumn(name = "Product_id")
  @JsonIgnore
  private List<ProductRelation> productRelations;

  @JsonIgnore
  @OneToMany(mappedBy = "productOffering", cascade = CascadeType.ALL)
  private List<Eligibility> eligibilities;

  @Column(name = "eligibility_id", insertable = false, updatable = true)
  private Integer eligibility_id;
}
