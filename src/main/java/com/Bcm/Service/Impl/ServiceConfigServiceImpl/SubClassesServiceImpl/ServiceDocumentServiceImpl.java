package com.Bcm.Service.Impl.ServiceConfigServiceImpl.SubClassesServiceImpl;


import com.Bcm.Model.ServiceABE.SubClasses.ServiceDocument;
import com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo.ServiceDocumentRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.ServiceDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDocumentServiceImpl implements ServiceDocumentService {


    @Autowired
    ServiceDocumentRepository serviceDocumentRepository;

    @Autowired
    ServiceDocumentService serviceDocumentService;

    @Override
    public ServiceDocument create(ServiceDocument ServiceDocument) {
        return serviceDocumentRepository.save(ServiceDocument);
    }

    @Override
    public List<ServiceDocument> read() {
        return serviceDocumentRepository.findAll();
    }


    @Override
    public ServiceDocument update(int SD_code, ServiceDocument updatedServiceDocument) {
        Optional<ServiceDocument> existingServiceDocumentOptional = serviceDocumentRepository.findById(SD_code);

        if (existingServiceDocumentOptional.isPresent()) {
            ServiceDocument existingServiceDocument = existingServiceDocumentOptional.get();
            existingServiceDocument.setName(updatedServiceDocument.getName());
            return serviceDocumentRepository.save(existingServiceDocument);
        } else {
            throw new RuntimeException("Could not find Group Dimension with ID: " + SD_code);
        }
    }


    @Override
    public String delete(int SD_code) {
        serviceDocumentRepository.deleteById(SD_code);
        return ("Group Dimension was successfully deleted");
    }

    @Override
    public ServiceDocument findById(int SD_code) {
        Optional<ServiceDocument> optionalServiceDocument = serviceDocumentRepository.findById(SD_code);
        return optionalServiceDocument.orElseThrow(() -> new RuntimeException("ServiceDocument with ID " + SD_code + " not found"));
    }


    @Override
    public List<ServiceDocument> searchByKeyword(String name) {
        return serviceDocumentRepository.searchByKeyword(name);
    }


}
