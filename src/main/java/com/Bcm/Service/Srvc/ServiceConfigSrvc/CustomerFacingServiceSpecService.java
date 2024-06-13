package com.Bcm.Service.Srvc.ServiceConfigSrvc;

import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CustomerFacingServiceSpecService {
  CustomerFacingServiceSpec create(CustomerFacingServiceSpec customerFacingServiceSpec);

  List<CustomerFacingServiceSpec> read();

  CustomerFacingServiceSpec update(int serviceId, CustomerFacingServiceSpec customerFacingServiceSpec);

  String delete(int serviceId);

  CustomerFacingServiceSpec findById(int serviceId);

  boolean findByNameexist(String name);

  CustomerFacingServiceSpec changeServiceStatus(int serviceId);

  boolean existsByName(String name);
}
