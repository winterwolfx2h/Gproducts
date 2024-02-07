package com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceStatusService {

    ServiceStatus create(ServiceStatus serviceStatus);

    List<ServiceStatus> read();

    ServiceStatus update(int SS_code, ServiceStatus serviceStatus);

    String delete(int SS_code);

    ServiceStatus findById(int SS_code);

    List<ServiceStatus> searchByKeyword(String name);
}
