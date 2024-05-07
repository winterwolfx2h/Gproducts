package com.Bcm.Model.ServiceABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceFacingServiceSpecDTO {
    private int Rfss_code;
    private String name;
    private String description;
    private Date validFor;
    private String status;
}
