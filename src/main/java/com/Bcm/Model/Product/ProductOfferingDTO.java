package com.Bcm.Model.Product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOfferingDTO {

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

    @Column(name = "sellIndicator", nullable = true)
    private Boolean sellIndicator;

    @Column(name = "quantity_Indicator", nullable = true)
    private String quantityIndicator;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "externalId", nullable = false)
    private String externalId;

    @Column(name = "market", nullable = true)
    private String markets;

    @Column(name = "submarket", nullable = true)
    private String submarkets;
}