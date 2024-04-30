package com.Bcm.Model.ProductResourceABE;

import com.Bcm.Model.ServiceABE.ResourceFacingServiceSpec;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "LogicalResource")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogicalResource extends ResourceFacingServiceSpec {

    @Column(name = "logicalResourceType", nullable = false)
    private String logicalResourceType;

    @Column(name = "logicalResourceFormat", nullable = false)
    private String logicalResourceFormat;

    @Column(name = "serviceId", nullable = false)
    private String serviceId;
}
