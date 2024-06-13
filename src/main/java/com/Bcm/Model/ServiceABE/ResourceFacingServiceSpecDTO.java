package com.Bcm.Model.ServiceABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceFacingServiceSpecDTO {
  private int Rfss_code;
  private int externalNPCode;
  private int customerFacingServiceSpec;
  private int logicalResource;
}
