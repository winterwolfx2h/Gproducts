package com.Bcm.Model.ServiceABE.SubClasses;

import com.Bcm.Model.ServiceABE.ServiceDocumentConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "CustomerType")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int CT_code;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "serviceDocumentConfig_id")
    private ServiceDocumentConfig serviceDocumentConfig;
}