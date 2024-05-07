package com.Bcm.Model.ServiceABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerFacingServiceSpecDTO {
    private int CFSS_code;
    private String externalId;
    private String numPlanCode;
    private String serviceSpecType;
    private String status;
    private String description;
    private List<ResourceFacingServiceSpecDTO> resourceFacingServiceSpec;
}
