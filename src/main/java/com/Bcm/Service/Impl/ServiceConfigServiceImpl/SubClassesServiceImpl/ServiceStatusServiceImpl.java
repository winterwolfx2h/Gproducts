package com.Bcm.Service.Impl.ServiceConfigServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceStatus;
import com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo.ServiceStatusRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.ServiceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceStatusServiceImpl implements ServiceStatusService {


    @Autowired
    ServiceStatusRepository serviceStatusRepository;

    @Autowired
    ServiceStatusService serviceStatusService;

    @Override
    public ServiceStatus create(ServiceStatus ServiceStatus) {
        return serviceStatusRepository.save(ServiceStatus);
    }

    @Override
    public List<ServiceStatus> read() {
        return serviceStatusRepository.findAll();
    }


    @Override
    public ServiceStatus update(int SS_code, ServiceStatus updatedServiceStatus) {
        Optional<ServiceStatus> existingServiceStatusOptional = serviceStatusRepository.findById(SS_code);

        if (existingServiceStatusOptional.isPresent()) {
            ServiceStatus existingServiceStatus = existingServiceStatusOptional.get();
            existingServiceStatus.setName(updatedServiceStatus.getName());
            return serviceStatusRepository.save(existingServiceStatus);
        } else {
            throw new RuntimeException("Could not find Group Dimension with ID: " + SS_code);
        }
    }


    @Override
    public String delete(int SS_code) {
        serviceStatusRepository.deleteById(SS_code);
        return ("Group Dimension was successfully deleted");
    }

    @Override
    public ServiceStatus findById(int SS_code) {
        Optional<ServiceStatus> optionalServiceStatus = serviceStatusRepository.findById(SS_code);
        return optionalServiceStatus.orElseThrow(() -> new RuntimeException("ServiceStatus with ID " + SS_code + " not found"));
    }


    @Override
    public List<ServiceStatus> searchByKeyword(String name) {
        return serviceStatusRepository.searchByKeyword(name);
    }


}
