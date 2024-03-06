package com.Bcm.Model.ServiceABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ServiceSpecConfig")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceSpecConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SSC_code", nullable = false)
    private int SSC_code;

    @Column(name = "serviceCode", nullable = false, columnDefinition = "int default 0")
    private String serviceCode;

    @Column(name = "externalId", nullable = false, columnDefinition = "int default 0")
    private String externalId;

    @Column(name = "numPlanCode", nullable = false, columnDefinition = "int default 0")
    private String numPlanCode;

    @Column(name = "serviceSpecName", nullable = false)
    private String serviceSpecName;

    @Column(name = "serviceSpecType", nullable = false)
    private String serviceSpecType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "description", nullable = false)
    private String description;
}
