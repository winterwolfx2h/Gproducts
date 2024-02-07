package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Model.ServiceABE.ServiceDocumentConfig;
import com.Bcm.Repository.ServiceConfigRepo.ServiceDocumentConfigRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceDocumentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDocumentConfigServiceImpl implements ServiceDocumentConfigService {

    @Autowired
     ServiceDocumentConfigRepository ServiceDocumentConfigRepository;

    @Autowired
     ServiceDocumentConfigService serviceDocumentConfigService;

    @Override
    public ServiceDocumentConfig create(ServiceDocumentConfig ServiceDocumentConfig) {
        return ServiceDocumentConfigRepository.save(ServiceDocumentConfig);
    }

    @Override
    public List<ServiceDocumentConfig> read() {
        return ServiceDocumentConfigRepository.findAll();
    }

    @Override
    public ServiceDocumentConfig update(int SDC_code, ServiceDocumentConfig updatedServiceDocumentConfig) {
        Optional<ServiceDocumentConfig> existingServiceDocumentConfigOptional = ServiceDocumentConfigRepository.findById(SDC_code);

        if (existingServiceDocumentConfigOptional.isPresent()) {
            ServiceDocumentConfig existingServiceDocumentConfig = existingServiceDocumentConfigOptional.get();
            existingServiceDocumentConfig.setServiceDocument(updatedServiceDocumentConfig.getServiceDocument());
            existingServiceDocumentConfig.setReason(updatedServiceDocumentConfig.getReason());
            existingServiceDocumentConfig.setCustomerType(updatedServiceDocumentConfig.getCustomerType());
            existingServiceDocumentConfig.setOptional(updatedServiceDocumentConfig.isOptional());

            return ServiceDocumentConfigRepository.save(existingServiceDocumentConfig);
        } else {
            throw new RuntimeException("Could not find ServiceDocumentConfig Specification with ID: " + SDC_code);
        }
    }

    @Override
    public String delete(int SDC_code) {
        ServiceDocumentConfigRepository.deleteById(SDC_code);
        return ("ServiceDocumentConfig Specification was successfully deleted");
    }

    @Override
    public ServiceDocumentConfig findById(int SDC_code) {
        Optional<ServiceDocumentConfig> optionalPlan = ServiceDocumentConfigRepository.findById(SDC_code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("ServiceDocumentConfig Specification with ID " + SDC_code + " not found"));
    }
}
