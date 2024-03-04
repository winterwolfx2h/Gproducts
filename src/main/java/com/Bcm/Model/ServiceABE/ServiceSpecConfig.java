package com.Bcm.Model.ServiceABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ServiceSpecConfig")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceSpecConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int SSC_code;

    @Column(name = "serviceCode", nullable = false)
    private String serviceCode;

    @Column(name = "externalId", nullable = false)
    private int externalId;

    @Column(name = "numPlanCode", nullable = false)
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
