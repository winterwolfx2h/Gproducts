package com.Bcm.Model.ServiceABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


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

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "resourceFacingServiceSpec", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ResourceFacingServiceSpec resourceFacingServiceSpec;
}
