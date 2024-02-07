package com.Bcm.Model.ServiceABE;

import com.Bcm.Model.ServiceABE.SubClasses.DependentService;
import com.Bcm.Model.ServiceABE.SubClasses.ServiceSpecType;
import com.Bcm.Model.ServiceABE.SubClasses.ServiceStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @JsonIgnore
    @Column(name = "externalSystemId", nullable = false)
    private int exSysId;

    @Column(name = "serviceSpecName", nullable = false)
    private String serviceSpecName;

    @Column(name = "serviceSpecType", nullable = false)
    @OneToMany(mappedBy = "serviceSpecConfig")
    private List<ServiceSpecType> serviceSpecType;

    @Column(name = "serviceCode", nullable = true)
    private int serviceCode;

    @Column(name = "dependentService", nullable = false)
    @OneToMany(mappedBy = "serviceSpecConfig")
    private List<DependentService> dependentService;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "description", nullable = false)
    private Date description;

    @Column(name = "status", nullable = false)
    @OneToMany(mappedBy = "serviceSpecConfig")
    private List<ServiceStatus> status;

}
