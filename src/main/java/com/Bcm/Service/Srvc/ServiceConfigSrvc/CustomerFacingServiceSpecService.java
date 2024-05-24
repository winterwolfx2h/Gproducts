package com.Bcm.Service.Srvc.ServiceConfigSrvc;

import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpecDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerFacingServiceSpecService {
    CustomerFacingServiceSpec create(CustomerFacingServiceSpec customerFacingServiceSpec);

    List<CustomerFacingServiceSpec> read();

    CustomerFacingServiceSpec update(int serviceId, CustomerFacingServiceSpec customerFacingServiceSpec);

    String delete(int serviceId);

    CustomerFacingServiceSpec findById(int serviceId);

    boolean findByNameexist(String serviceSpecType);

    CustomerFacingServiceSpec changeServiceStatus(int serviceId);

    CustomerFacingServiceSpecDTO getCustomerFacingServiceSpecDTO(int serviceId);

    List<CustomerFacingServiceSpecDTO> getAllCustomerFacingServiceSpecDTOs();

    boolean existsByName(String name);

}

