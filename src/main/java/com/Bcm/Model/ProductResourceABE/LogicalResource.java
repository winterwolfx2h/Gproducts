package com.Bcm.Model.ProductResourceABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "LogicalResource")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogicalResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logResourceId", nullable = false)
    private int logResourceId;

    @Column(name = "logicalResourceType", nullable = false)
    private String logicalResourceType;

    @Column(name = "logicalResourceFromat", nullable = false)
    private String logicalResourceFromat;

    @Column(name = "serviceId", nullable = false)
    private String serviceId;

    @Column(name = "length", nullable = false)
    private String length;

}
