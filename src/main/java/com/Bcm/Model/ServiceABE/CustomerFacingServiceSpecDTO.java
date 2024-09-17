package com.Bcm.Model.ServiceABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerFacingServiceSpecDTO {
    private int serviceId;
    private String externalId;
    private String serviceSpecType;
    private String status;
    private String description;

    public CustomerFacingServiceSpecDTO(
            int serviceId,
            String name,
            String description,
            String serviceSpecType,
            String externalId,
            String logicalResource,
            String status) {
    }
}
