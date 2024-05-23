package com.Bcm.Model.Product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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
