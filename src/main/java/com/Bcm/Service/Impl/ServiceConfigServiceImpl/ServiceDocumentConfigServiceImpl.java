package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ServiceABE.ServiceDocumentConfig;
import com.Bcm.Repository.ServiceConfigRepo.ServiceDocumentConfigRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceDocumentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDocumentConfigServiceImpl implements ServiceDocumentConfigService {

    @Autowired
    ServiceDocumentConfigRepository ServiceDocumentConfigRepository;

    @Override
    public ServiceDocumentConfig create(ServiceDocumentConfig ServiceDocumentConfig) {
        validateNotNullFields(ServiceDocumentConfig);
        try {
            return ServiceDocumentConfigRepository.save(ServiceDocumentConfig);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating ServiceDocumentConfig", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating ServiceDocumentConfig", e);
        }
    }

    @Override
    public List<ServiceDocumentConfig> read() {
        try {
            return ServiceDocumentConfigRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading ServiceDocumentConfigs", e);
        }
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
            throw new ResourceNotFoundException("Could not find ServiceDocumentConfig Specification with ID: " + SDC_code);
        }
    }

    @Override
    public String delete(int SDC_code) {
        if (!ServiceDocumentConfigRepository.existsById(SDC_code)) {
            throw new ResourceNotFoundException("ServiceDocumentConfig with ID " + SDC_code + " not found");
        }

        try {
            ServiceDocumentConfigRepository.deleteById(SDC_code);
            return "ServiceDocumentConfig Specification with ID " + SDC_code + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting ServiceDocumentConfig with ID: " + SDC_code, e);
        }
    }

    @Override
    public ServiceDocumentConfig findById(int SDC_code) {
        try {
            return ServiceDocumentConfigRepository.findById(SDC_code)
                    .orElseThrow(() -> new ResourceNotFoundException("ServiceDocumentConfig Specification with ID " + SDC_code + " not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("ServiceDocumentConfig Specification with ID \"" + SDC_code + "\" not found", e);
        }
    }

    private void validateNotNullFields(ServiceDocumentConfig ServiceDocumentConfig) {
        if (ServiceDocumentConfig.getServiceDocument() == null || ServiceDocumentConfig.getReason() == null || ServiceDocumentConfig.getCustomerType() == null) {
            throw new InvalidInputException("ServiceDocument, Reason, and CustomerType cannot be null");

        }
    }
}