package com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc;


import com.Bcm.Model.ServiceABE.SubClasses.ServiceDocument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceDocumentService {

    ServiceDocument create(ServiceDocument serviceDocument);

    List<ServiceDocument> read();

    ServiceDocument update(int SD_code, ServiceDocument serviceDocument);

    String delete(int SD_code);

    ServiceDocument findById(int SD_code);

    List<ServiceDocument> searchByKeyword(String name);
}
