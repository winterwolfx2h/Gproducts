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
    @Column(name = "PR_id ", nullable = false)
    private int PR_id ;

    @Column(name = "physicalResourceType", nullable = false)
    private String physicalResourceType;

    @Column(name = "physicalResourceFormat", nullable = false)
    private String physicalResourceFormat;

    @Column(name = "status", nullable = false)
    private String status;
}

