package com.Bcm.Service.Srvc.ServiceConfigSrvc;

import com.Bcm.Model.ServiceABE.ServiceDocumentConfig;
import com.Bcm.Model.ServiceABE.ServiceSpecConfig;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ServiceDocumentConfigService {
    ServiceDocumentConfig create(ServiceDocumentConfig serviceDocumentConfig);

    List<ServiceDocumentConfig> read();

    ServiceDocumentConfig update(int SDC_code, ServiceDocumentConfig serviceDocumentConfig);

    String delete(int SDC_code);

    ServiceDocumentConfig findById(int SDC_code);

}
