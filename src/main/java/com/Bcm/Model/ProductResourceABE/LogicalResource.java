package com.Bcm.Model.ProductResourceABE;

import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "LogicalResource")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogicalResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LR_id", nullable = false)
    private int LR_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logicalResourceType", nullable = false)
    private String logicalResourceType;

    @Column(name = "logicalResourceFormat", nullable = false)
    private String logicalResourceFormat;

    @Column(name = "length", nullable = false)
    private String length;

    @Column(name = "generationType", nullable = false)
    private String generationType;

    @Column(name = "externalNPCode", nullable = true)
    private String externalNPCode;

    @Column(name = "status", nullable = false)
    private String status;


    @Column(name = "po_MarketCode", insertable = true, updatable = true)
    private Integer po_MarketCode;

    @Column(name = "po_SubMarketCode", insertable = true, updatable = true)
    private Integer po_SubMarketCode;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = CustomerFacingServiceSpec.class)
    @JoinColumn(name = "LR_id")
    @JsonIgnore
    private List<CustomerFacingServiceSpec> customerFacingServiceSpecs;
}
