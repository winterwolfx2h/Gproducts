package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Exception.*;
import com.Bcm.Model.ServiceABE.ServiceSpecConfig;
import com.Bcm.Repository.ServiceConfigRepo.ServiceSpecConfigRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceSpecConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ServiceSpecConfigServiceImpl implements ServiceSpecConfigService {

    final ServiceSpecConfigRepository serviceSpecConfigRepository;

    @Override
    public ServiceSpecConfig create(ServiceSpecConfig serviceSpecConfig) {
        validateNotNullFields(serviceSpecConfig);
        if (serviceSpecConfigRepository.findByServiceSpecType(serviceSpecConfig.getServiceSpecType()).isPresent()) {
            throw new ServiceAlreadyExistsException("Service with the same name already exists");
        }
        try {
            serviceSpecConfig.setStatus("Working state");
            return serviceSpecConfigRepository.save(serviceSpecConfig);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating ServiceSpecConfig", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating ServiceSpecConfig", e);
        }
    }

    @Override
    public List<ServiceSpecConfig> read() {
        try {
            return serviceSpecConfigRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading ServiceSpecConfigs", e);
        }
    }

    @Override
    public ServiceSpecConfig update(int SSC_code, ServiceSpecConfig updatedserviceSpecConfig) {
        Optional<ServiceSpecConfig> existingServiceSpecConfigOptional = serviceSpecConfigRepository.findById(SSC_code);

        if (existingServiceSpecConfigOptional.isPresent()) {
            ServiceSpecConfig existingServiceSpecConfig = existingServiceSpecConfigOptional.get();
            if (!existingServiceSpecConfig.getServiceSpecType().equals(updatedserviceSpecConfig.getServiceSpecType())) {
                if (serviceSpecConfigRepository.findByServiceSpecType(updatedserviceSpecConfig.getServiceSpecType()).isPresent()) {
                    throw new ServiceAlreadyExistsException("Service with the same name already exists");
                }
            }
            existingServiceSpecConfig.setExternalId(updatedserviceSpecConfig.getExternalId());
            existingServiceSpecConfig.setNumPlanCode(updatedserviceSpecConfig.getNumPlanCode());
            existingServiceSpecConfig.setServiceSpecType(updatedserviceSpecConfig.getServiceSpecType());
            existingServiceSpecConfig.setStatus(updatedserviceSpecConfig.getStatus());
            existingServiceSpecConfig.setDescription(updatedserviceSpecConfig.getDescription());
            return serviceSpecConfigRepository.save(existingServiceSpecConfig);
        } else {
            throw new ResourceNotFoundException("Could not find ServiceSpecConfig with ID: " + SSC_code);
        }
    }


    @Override
    public String delete(int SSC_code) {
        if (!serviceSpecConfigRepository.existsById(SSC_code)) {
            throw new ResourceNotFoundException("ServiceSpecConfig with ID " + SSC_code + " not found");
        }

        try {
            serviceSpecConfigRepository.deleteById(SSC_code);
            return "ServiceSpecConfig  with ID " + SSC_code + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting ServiceSpecConfig with ID: " + SSC_code, e);
        }
    }

    @Override
    public ServiceSpecConfig findById(int SSC_code) {
        try {
            return serviceSpecConfigRepository.findById(SSC_code)
                    .orElseThrow(() -> new ResourceNotFoundException("ServiceSpecConfig  with ID " + SSC_code + " not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("ServiceSpecConfig  with ID \"" + SSC_code + "\" not found", e);
        }
    }

    public ServiceSpecConfig changeServiceStatus(int SSC_code) {
        try {
            ServiceSpecConfig existingService = findById(SSC_code);

            switch (existingService.getStatus()) {
                case "Working state":
                    existingService.setStatus("Validated");
                    break;

                case "Validated":
                    existingService.setStatus("Suspended");
                    break;

                case "Suspended":
                    throw new ServiceLogicException("ServiceSpecConfig " + existingService.getServiceSpecType() + " isn't fit to be offered for sale anymore.");

                default:
                    throw new InvalidInputException("Invalid status transition.");
            }

            return serviceSpecConfigRepository.save(existingService);

        } catch (ServiceLogicException e) {
            throw e;
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("ServiceSpecConfig with ID \"" + SSC_code + "\" not found", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while changing ServiceSpecConfig status", e);
        }
    }


    private void validateNotNullFields(ServiceSpecConfig serviceSpecConfig) {
        if (serviceSpecConfig.getServiceSpecType() == null) {
            throw new InvalidInputException("ServiceSpecName, ServiceSpecType, and ServiceCode cannot be null");
        }
    }
}
