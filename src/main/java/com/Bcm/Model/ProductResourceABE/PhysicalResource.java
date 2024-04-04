package com.Bcm.Model.ProductResourceABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PhysicalResource")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhysicalResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phyResourceId", nullable = false)
    private int phyResourceId;

    @Column(name = "physicalResourceType", nullable = false)
    private String physicalResourceType;

    @Column(name = "physicalResourceFromat", nullable = false)
    private String physicalResourceFromat;

    @Column(name = "serviceId", nullable = false)
    private String serviceId;
}

