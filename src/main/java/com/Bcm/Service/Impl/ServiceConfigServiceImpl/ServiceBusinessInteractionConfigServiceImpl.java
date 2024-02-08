package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ServiceABE.ServiceBusinessInteractionConfig;
import com.Bcm.Repository.ServiceConfigRepo.ServiceBusinessInteractionConfigRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceBusinessInteractionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceBusinessInteractionConfigServiceImpl implements ServiceBusinessInteractionConfigService {

    @Autowired
    private ServiceBusinessInteractionConfigRepository ServiceBusinessInteractionConfigRepository;

    @Override
    public ServiceBusinessInteractionConfig create(ServiceBusinessInteractionConfig ServiceBusinessInteractionConfig) {
        validateNotNullFields(ServiceBusinessInteractionConfig);
        try {
            return ServiceBusinessInteractionConfigRepository.save(ServiceBusinessInteractionConfig);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating ServiceBusinessInteractionConfig", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating ServiceBusinessInteractionConfig", e);
        }
    }

    @Override
    public List<ServiceBusinessInteractionConfig> read() {
        try {
            return ServiceBusinessInteractionConfigRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading ServiceBusinessInteractionConfigs", e);
        }
    }

    @Override
    public ServiceBusinessInteractionConfig update(int SBIC_code, ServiceBusinessInteractionConfig updatedServiceBusinessInteractionConfig) {
        Optional<ServiceBusinessInteractionConfig> existingServiceBusinessInteractionConfigOptional = ServiceBusinessInteractionConfigRepository.findById(SBIC_code);

        if (existingServiceBusinessInteractionConfigOptional.isPresent()) {
            ServiceBusinessInteractionConfig existingServiceBusinessInteractionConfig = existingServiceBusinessInteractionConfigOptional.get();
            existingServiceBusinessInteractionConfig.setBusinessInteraction(updatedServiceBusinessInteractionConfig.getBusinessInteraction());
            existingServiceBusinessInteractionConfig.setProvisionName(updatedServiceBusinessInteractionConfig.getProvisionName());
            existingServiceBusinessInteractionConfig.setStartDate(updatedServiceBusinessInteractionConfig.getStartDate());
            existingServiceBusinessInteractionConfig.setEndDate(updatedServiceBusinessInteractionConfig.getEndDate());
            existingServiceBusinessInteractionConfig.setSalesChannel(updatedServiceBusinessInteractionConfig.getSalesChannel());

            return ServiceBusinessInteractionConfigRepository.save(existingServiceBusinessInteractionConfig);
        } else {
            throw new ResourceNotFoundException("Could not find ServiceBusinessInteractionConfig Specification with ID: " + SBIC_code);
        }
    }

    @Override
    public String delete(int SBIC_code) {
        ServiceBusinessInteractionConfigRepository.deleteById(SBIC_code);
        return ("ServiceBusinessInteractionConfig Specification was successfully deleted");
    }

    @Override
    public ServiceBusinessInteractionConfig findById(int SBIC_code) {
        Optional<ServiceBusinessInteractionConfig> optionalPlan = ServiceBusinessInteractionConfigRepository.findById(SBIC_code);
        return optionalPlan.orElseThrow(() -> new ResourceNotFoundException("ServiceBusinessInteractionConfig Specification with ID " + SBIC_code + " not found"));
    }

    private void validateNotNullFields(ServiceBusinessInteractionConfig ServiceBusinessInteractionConfig) {
        if (ServiceBusinessInteractionConfig.getBusinessInteraction() == null || ServiceBusinessInteractionConfig.getProvisionName() == null || ServiceBusinessInteractionConfig.getSalesChannel() == null) {
            throw new InvalidInputException("BusinessInteraction, ProvisionName, and SalesChannel cannot be null");
        }
    }
}
