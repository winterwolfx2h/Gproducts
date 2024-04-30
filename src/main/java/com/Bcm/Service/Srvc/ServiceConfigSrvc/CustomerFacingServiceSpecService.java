package com.Bcm.Service.Srvc.ServiceConfigSrvc;

import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerFacingServiceSpecService {
    CustomerFacingServiceSpec create(CustomerFacingServiceSpec customerFacingServiceSpec);

    List<CustomerFacingServiceSpec> read();

    CustomerFacingServiceSpec update(int CFSS_code, CustomerFacingServiceSpec customerFacingServiceSpec);

    String delete(int CFSS_code);

    CustomerFacingServiceSpec findById(int CFSS_code);

    boolean findByNameexist(String serviceSpecType);

    CustomerFacingServiceSpec changeServiceStatus(int CFSS_code);
}
