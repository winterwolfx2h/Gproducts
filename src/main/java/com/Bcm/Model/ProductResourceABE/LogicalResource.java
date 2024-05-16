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
    @Column(name = "LR_id", nullable = false)
    private int LR_id;

    @Column(name = "logicalResourceType", nullable = false)
    private String logicalResourceType;

    @Column(name = "logicalResourceFormat", nullable = false)
    private String logicalResourceFormat;

    @Column(name = "status", nullable = false)
    private String status;
}
