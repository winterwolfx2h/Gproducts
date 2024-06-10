package com.Bcm.Model.Product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data

public class GeneralInfoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_id", nullable = false)
    private int Product_id;

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

    @Column(name = "detailedDescription", nullable = true)
    private String detailedDescription;

    @Column(name = "poType", nullable = false)
    private String poType;

    @Column(name = "parent", nullable = true)
    private String parent;

    @Column(name = "workingStep", nullable = false)
    private String workingStep = "GeneralInfo";

    @Column(name = "sellIndicator", nullable = true)
    private Boolean sellIndicator;

    @Column(name = "quantity_Indicator", nullable = true)
    private Boolean quantityIndicator;

    @Column(name = "category", nullable = true)
    private String category;

    @Column(name = "BS_externalId", nullable = true)
    private String BS_externalId;

    @Column(name = "CS_externalId", nullable = true)
    private String CS_externalId;

    @Column(name = "status", nullable = false)
    private String status;

    @Pattern(regexp = "^(PO-PARENT|PO-CHILD)$", message = "invalid code")
    @Column(name = "poParent_Child", nullable = true)
    private String poParent_Child;

//    @ManyToMany
//    @JsonIgnore
//    @JoinTable(
//            name = "productoffering_entity",
//            joinColumns = @JoinColumn(name = "Product_id"),
//            inverseJoinColumns = @JoinColumn(name = "entityCode"))
//    Set<EligibilityEntity> entities;
//
//    @ManyToMany
//    @JsonIgnore
//    @JoinTable(
//            name = "productoffering_channel",
//            joinColumns = @JoinColumn(name = "Product_id"),
//            inverseJoinColumns = @JoinColumn(name = "po_ChannelCode"))
//    Set<Channel> channels;

}
