package com.Bcm.Service.Srvc.ServiceConfigSrvc;


import com.Bcm.Model.ServiceABE.ResourceFacingServiceSpec;

import java.util.List;

public interface ResourceFacingServiceSpecService {

    ResourceFacingServiceSpec create(ResourceFacingServiceSpec resourceFacingServiceSpec);

    List<ResourceFacingServiceSpec> read();

    ResourceFacingServiceSpec update(int Rfss_code, ResourceFacingServiceSpec resourceFacingServiceSpec);

    String delete(int Rfss_code);

    ResourceFacingServiceSpec findById(int Rfss_code);

    boolean findByexternalNPCodeexist(String externalNPCode);

    //ResourceFacingServiceSpec changeServiceStatus(int Rfss_code);


}
