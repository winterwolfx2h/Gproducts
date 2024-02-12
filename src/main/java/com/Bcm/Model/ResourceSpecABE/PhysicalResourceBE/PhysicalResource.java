package com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "PhysicalResource")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhysicalResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int PRID;

    @Column(name = "manufactureDate", nullable = false)
    private Date manufactureDate;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weightUnits", nullable = false)
    private String weightUnits;

    @Column(name = "serialNumber", nullable = false)
    private Long serialNumber;

}
