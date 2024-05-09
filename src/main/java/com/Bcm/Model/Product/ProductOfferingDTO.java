package com.Bcm.Model.Product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOfferingDTO {

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

    @Column(name = "family_name", nullable = false)
    private String familyName;

    @Column(name = "subFamily", nullable = false)
    private String subFamily;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "poType", nullable = false)
    private String poType;

    @Column(name = "externalId", nullable = false)
    private String externalId;

    @ElementCollection
    @CollectionTable(name = "product_offering_markets", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "name")
    private List<String> markets;

    @ElementCollection
    @CollectionTable(name = "product_offering_submarkets", joinColumns = @JoinColumn(name = "Product_id"))
    @Column(name = "name")
    private List<String> submarkets;

}


