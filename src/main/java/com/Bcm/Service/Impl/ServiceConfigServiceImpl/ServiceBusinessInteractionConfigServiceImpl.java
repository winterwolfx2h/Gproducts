package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Model.ServiceABE.ServiceBusinessInteractionConfig;
import com.Bcm.Repository.ServiceConfigRepo.ServiceBusinessInteractionConfigRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceBusinessInteractionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceBusinessInteractionConfigServiceImpl implements ServiceBusinessInteractionConfigService {

    @Autowired
    private ServiceBusinessInteractionConfigRepository ServiceBusinessInteractionConfigRepository;

    @Autowired
    private ServiceBusinessInteractionConfigService serviceBusinessInteractionConfigService;

    @Override
    public ServiceBusinessInteractionConfig create(ServiceBusinessInteractionConfig ServiceBusinessInteractionConfig) {
        return ServiceBusinessInteractionConfigRepository.save(ServiceBusinessInteractionConfig);
    }

    @Override
    public List<ServiceBusinessInteractionConfig> read() {
        return ServiceBusinessInteractionConfigRepository.findAll();
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
            throw new RuntimeException("Could not find ServiceBusinessInteractionConfig Specification with ID: " + SBIC_code);
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
        return optionalPlan.orElseThrow(() -> new RuntimeException("ServiceBusinessInteractionConfig Specification with ID " + SBIC_code + " not found"));
    }
}
