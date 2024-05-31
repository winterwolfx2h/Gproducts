package com.Bcm.Model.ServiceABE;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Table(name = "CustomerFacingServiceSpec")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerFacingServiceSpec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceId", nullable = false)
    private int serviceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "serviceSpecType", nullable = false)
    private String serviceSpecType;

    @Column(name = "externalId", nullable = false)
    private String externalId;

    @Column(name = "LR_id", nullable = true)
    private int LR_id;

    @Column(name = "status", nullable = false)
    private String status;


    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOffering.class)
    @JoinColumn(name = "serviceId")
    @JsonIgnore
    private List<ProductOffering> productOfferings;


}
