package com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalConnector;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ConnectorType")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConnectorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int CnTID;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "physicalConnector_id")
    private PhysicalConnector physicalConnector;
}