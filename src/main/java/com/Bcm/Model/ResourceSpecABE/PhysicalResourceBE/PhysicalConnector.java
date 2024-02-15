package com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.CableType;
import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.ConnectorType;
import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "PhysicalConnector")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhysicalConnector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int PCnID;

    @Column(name = "connectorType", nullable = false)
    @OneToMany(mappedBy = "physicalConnector")
    private List<ConnectorType> connectorType;

    @Column(name = "gender", nullable = false)
    @OneToMany(mappedBy = "physicalConnector")
    private List<Gender> gender;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cableType", nullable = false)
    @OneToMany(mappedBy = "physicalConnector")
    private List<CableType> cableType;


}
