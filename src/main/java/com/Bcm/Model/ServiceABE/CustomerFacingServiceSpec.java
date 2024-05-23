package com.Bcm.Model.ServiceABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


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

    @Column(name = "logicalResource", nullable = true)
    private String logicalResource;

    @Column(name = "status", nullable = false)
    private String status;

}
