package com.Bcm.Service.Impl.ServiceConfigServiceImpl.SubClassesServiceImpl;


import com.Bcm.Model.ServiceABE.SubClasses.ServiceSpecType;
import com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo.ServiceSpecTypeRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.ServiceSpecTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceSpecTypeServiceImpl implements ServiceSpecTypeService {


    @Autowired
    ServiceSpecTypeRepository serviceSpecTypeRepository;

    @Autowired
    ServiceSpecTypeService serviceSpecTypeService;

    @Override
    public ServiceSpecType create(ServiceSpecType ServiceSpecType) {
        return serviceSpecTypeRepository.save(ServiceSpecType);
    }

    @Override
    public List<ServiceSpecType> read() {
        return serviceSpecTypeRepository.findAll();
    }


    @Override
    public ServiceSpecType update(int SST_code, ServiceSpecType updatedServiceSpecType) {
        Optional<ServiceSpecType> existingServiceSpecTypeOptional = serviceSpecTypeRepository.findById(SST_code);

        if (existingServiceSpecTypeOptional.isPresent()) {
            ServiceSpecType existingServiceSpecType = existingServiceSpecTypeOptional.get();
            existingServiceSpecType.setName(updatedServiceSpecType.getName());
            return serviceSpecTypeRepository.save(existingServiceSpecType);
        } else {
            throw new RuntimeException("Could not find Group Dimension with ID: " + SST_code);
        }
    }


    @Override
    public String delete(int SST_code) {
        serviceSpecTypeRepository.deleteById(SST_code);
        return ("Group Dimension was successfully deleted");
    }

    @Override
    public ServiceSpecType findById(int SST_code) {
        Optional<ServiceSpecType> optionalServiceSpecType = serviceSpecTypeRepository.findById(SST_code);
        return optionalServiceSpecType.orElseThrow(() -> new RuntimeException("ServiceSpecType with ID " + SST_code + " not found"));
    }


    @Override
    public List<ServiceSpecType> searchByKeyword(String name) {
        return serviceSpecTypeRepository.searchByKeyword(name);
    }


}
