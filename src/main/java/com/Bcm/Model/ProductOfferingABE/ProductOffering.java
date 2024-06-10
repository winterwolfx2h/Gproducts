package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "ProductOffering")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductOffering extends Product {

    @ManyToMany
    @JoinTable(
            name = "po_businessProcess",
            joinColumns = @JoinColumn(name = "Product_id"),
            inverseJoinColumns = @JoinColumn(name = "businessProcess_id"))
    Set<BusinessProcess> businessProcess;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "productoffering_entity",
            joinColumns = @JoinColumn(name = "Product_id"),
            inverseJoinColumns = @JoinColumn(name = "entityCode"))
    Set<EligibilityEntity> entities;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "productoffering_channel",
            joinColumns = @JoinColumn(name = "Product_id"),
            inverseJoinColumns = @JoinColumn(name = "po_ChannelCode"))
    Set<Channel> channels;
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
    @Pattern(regexp = "^(PO_PARENT|PO_CHILD)$", message = "invalid code")
    @Column(name = "poParent_Child")
    private String poParent_Child;
    @Column(name = "markets", nullable = true)
    private String markets;
    @Column(name = "submarkets", nullable = true)
    private String submarkets;
    @Column(name = "PR_id", insertable = false, updatable = true)
    private Integer PR_id;
    @Column(name = "serviceId", insertable = false, updatable = true)
    private Integer serviceId;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOfferRelation.class)
    @JoinColumn(name = "Product_id")
    @JsonIgnore
    private List<ProductOfferRelation> productOfferRelations;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Eligibility.class)
    @JoinColumn(name = "Product_id")
    @JsonIgnore
    private List<Eligibility> eligibilities;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = BusinessProcess.class)
    @JoinColumn(name = "Product_id")
    @JsonIgnore
    private List<BusinessProcess> businessProcesses;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductRelation.class)
    @JoinColumn(name = "Product_id")
    @JsonIgnore
    private List<ProductRelation> productRelations;
    @Column(name = "eligibility_id", insertable = false, updatable = true)
    private Integer eligibility_id;

    public enum poParent_Child {
        PO_Parent,
        PO_Child
    }
}
