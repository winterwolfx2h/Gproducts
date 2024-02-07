package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Model.ServiceABE.ServiceSpecConfig;
import com.Bcm.Repository.ServiceConfigRepo.ServiceSpecConfigRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceSpecConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceSpecConfigServiceImpl implements ServiceSpecConfigService {

    @Autowired
    ServiceSpecConfigRepository serviceSpecConfigRepository;

    @Autowired
    ServiceSpecConfigService serviceSpecConfigService;

    @Override
    public ServiceSpecConfig create(ServiceSpecConfig serviceSpecConfig) {
        return serviceSpecConfigRepository.save(serviceSpecConfig);
    }

    @Override
    public List<ServiceSpecConfig> read() {
        return serviceSpecConfigRepository.findAll();
    }

    @Override
    public ServiceSpecConfig update(int SSC_code, ServiceSpecConfig updatedserviceSpecConfig) {
        Optional<ServiceSpecConfig> existingServiceSpecConfigOptional = serviceSpecConfigRepository.findById(SSC_code);

        if (existingServiceSpecConfigOptional.isPresent()) {
            ServiceSpecConfig existingServiceSpecConfig = existingServiceSpecConfigOptional.get();
            existingServiceSpecConfig.setServiceSpecName(updatedserviceSpecConfig.getServiceSpecName());
            existingServiceSpecConfig.setServiceSpecType(updatedserviceSpecConfig.getServiceSpecType());
            existingServiceSpecConfig.setServiceCode(updatedserviceSpecConfig.getServiceCode());
            existingServiceSpecConfig.setDependentService(updatedserviceSpecConfig.getDependentService());
            existingServiceSpecConfig.setStartDate(updatedserviceSpecConfig.getStartDate());
            existingServiceSpecConfig.setEndDate(updatedserviceSpecConfig.getEndDate());
            existingServiceSpecConfig.setDescription(updatedserviceSpecConfig.getDescription());
            existingServiceSpecConfig.setStatus(updatedserviceSpecConfig.getStatus());

            return serviceSpecConfigRepository.save(existingServiceSpecConfig);
        } else {
            throw new RuntimeException("Could not find ServiceSpecConfig Specification with ID: " + SSC_code);
        }
    }

    @Override
    public String delete(int SSC_code) {
        serviceSpecConfigRepository.deleteById(SSC_code);
        return ("ServiceSpecConfig Specification was successfully deleted");
    }

    @Override
    public ServiceSpecConfig findById(int SSC_code) {
        Optional<ServiceSpecConfig> optionalPlan = serviceSpecConfigRepository.findById(SSC_code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("ServiceSpecConfig Specification with ID " + SSC_code + " not found"));
    }
}
