package com.Bcm.Model.ServiceABE;

import com.Bcm.Model.ServiceABE.SubClasses.CustomerType;
import com.Bcm.Model.ServiceABE.SubClasses.ServiceDocument;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ServiceDocumentConfig")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ServiceDocumentConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int SDC_code;

    @Column(name = "document", nullable = false)
    @OneToMany(mappedBy = "serviceDocumentConfig")
    private List<ServiceDocument> serviceDocument;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "customerType", nullable = false)
    @OneToMany(mappedBy = "serviceDocumentConfig")
    private List<CustomerType> customerType;

    @Column(name = "isOptional", nullable = false)
    private boolean isOptional;

}