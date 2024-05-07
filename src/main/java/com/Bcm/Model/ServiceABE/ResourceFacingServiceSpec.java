package com.Bcm.Model.ServiceABE;


import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "validFor", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Timestamp
    private Date validFor;

    @Column(name = "status", nullable = false)
    private String status;
}