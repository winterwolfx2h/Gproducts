package com.Bcm.Model.ResourceSpecABE.ResourceConfigBE;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ROUStatus;
import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ROUUsageStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "ResourceOrderAndUsage")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceOrderAndUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int ROUID;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "status", nullable = false)
    @OneToMany(mappedBy = "resourceOrderAndUsage")
    private List<ROUStatus> status;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "usagestatus", nullable = false)
    @OneToMany(mappedBy = "resourceOrderAndUsage")
    private List<ROUUsageStatus> usageStatus;

    @Column(name = "usageDate", nullable = false)
    private Date usageDate;


}