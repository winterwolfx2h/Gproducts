package com.Bcm.Model.ServiceABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Table(name = "CustomerFacingServiceSpec")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerFacingServiceSpec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CFSS_code", nullable = false)
    private int CFSS_code;

    @Column(name = "externalId", nullable = false)
    private String externalId;

    @Column(name = "numPlanCode", nullable = false)
    private String numPlanCode;

    @Column(name = "serviceSpecType", nullable = false)
    private String serviceSpecType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "description", nullable = false)
    private String description;

    @ElementCollection
    @CollectionTable(name = "CFSS_RFSS", joinColumns = @JoinColumn(name = "CFSS_code"))
    @Column(name = "rfss_name")
    private List<String> resourceFacingServiceSpec = new ArrayList<>();
    ;
}
