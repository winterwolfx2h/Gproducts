package com.Bcm.Service.Srvc.ServiceConfigSrvc;

import com.Bcm.Model.ServiceABE.ServiceBusinessInteractionConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceBusinessInteractionConfigService {
    ServiceBusinessInteractionConfig create(ServiceBusinessInteractionConfig serviceBusinessInteractionConfig);

    List<ServiceBusinessInteractionConfig> read();

    ServiceBusinessInteractionConfig update(int SBIC_code, ServiceBusinessInteractionConfig serviceBusinessInteractionConfig);

    String delete(int SBIC_code);

    ServiceBusinessInteractionConfig findById(int SBIC_code);

}