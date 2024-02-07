package com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceSpecType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceSpecTypeService {

    ServiceSpecType create(ServiceSpecType serviceSpecType);

    List<ServiceSpecType> read();

    ServiceSpecType update(int SST_code, ServiceSpecType serviceSpecType);

    String delete(int SST_code);

    ServiceSpecType findById(int SST_code);

    List<ServiceSpecType> searchByKeyword(String name);
}
