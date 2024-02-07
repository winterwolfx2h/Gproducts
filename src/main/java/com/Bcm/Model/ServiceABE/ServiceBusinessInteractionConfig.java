package com.Bcm.Model.ServiceABE;

import com.Bcm.Model.ServiceABE.SubClasses.BusinessInteraction;
import com.Bcm.Model.ServiceABE.SubClasses.SalesChannel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "ServiceBusinessInteractionConfig")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceBusinessInteractionConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int SBIC_code;

    @Column(name = "businessInteraction", nullable = false)
    @OneToMany(mappedBy = "serviceBusinessInteractionConfig")
    private List<BusinessInteraction> businessInteraction;

    @Column(name = "provisionName", nullable = false)
    private String provisionName;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "saleChannel", nullable = false)
    @OneToMany(mappedBy = "serviceBusinessInteractionConfig")
    private List<SalesChannel> salesChannel;
}
