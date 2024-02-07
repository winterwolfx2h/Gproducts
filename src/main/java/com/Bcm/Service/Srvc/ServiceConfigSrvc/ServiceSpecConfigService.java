package com.Bcm.Service.Srvc.ServiceConfigSrvc;

import com.Bcm.Model.ServiceABE.ServiceSpecConfig;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ServiceSpecConfigService {
    ServiceSpecConfig create(ServiceSpecConfig serviceSpecConfig);

    List<ServiceSpecConfig> read();

    ServiceSpecConfig update(int SSC_code, ServiceSpecConfig serviceSpecConfig);

    String delete(int SSC_code);

    ServiceSpecConfig findById(int SSC_code);

}
