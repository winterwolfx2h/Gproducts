package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ProductResource")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "PrResId", nullable = false)
    private int PrResId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logicalResourceType", nullable = true)
    private String logicalResourceType;

    @Column(name = "logicalResourceFromat", nullable = true)
    private String logicalResourceFromat;

    @Column(name = "lrServiceId", nullable = false)
    private String lrServiceId; // LR Service ID

    @Column(name = "prServiceId", nullable = false)
    private String prServiceId; // PR Service ID

    @Column(name = "physicalResourceType", nullable = false)
    private String physicalResourceType;

    @Column(name = "physicalResourceFromat", nullable = false)
    private String physicalResourceFromat;
}
