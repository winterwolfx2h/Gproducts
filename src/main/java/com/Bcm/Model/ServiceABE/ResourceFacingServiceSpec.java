package com.Bcm.Model.ServiceABE;

import com.Bcm.Model.ProductResourceABE.LogicalResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ResourceFacingServiceSpecification")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceFacingServiceSpec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Rfss_code", nullable = false)
    private int Rfss_code;

    @Column(name = "externalNPCode", nullable = false)
    private String externalNPCode;

    @ManyToOne
    @JoinColumn(name = "serviceId", referencedColumnName = "serviceId", nullable = false)
    private CustomerFacingServiceSpec customerFacingServiceSpec;

    @ManyToOne
    @JoinColumn(name = "LR_id", referencedColumnName = "LR_id", nullable = false)
    private LogicalResource logicalResource;
}
