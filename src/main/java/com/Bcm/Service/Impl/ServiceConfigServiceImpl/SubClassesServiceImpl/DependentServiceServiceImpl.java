package com.Bcm.Service.Impl.ServiceConfigServiceImpl.SubClassesServiceImpl;


import com.Bcm.Model.ServiceABE.SubClasses.DependentService;
import com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo.DependentServiceRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.DependentServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DependentServiceServiceImpl implements DependentServiceService {


    @Autowired
    DependentServiceRepository dependentServiceRepository;

    @Autowired
    DependentServiceService dependentServiceService;

    @Override
    public DependentService create(DependentService DependentService) {
        return dependentServiceRepository.save(DependentService);
    }

    @Override
    public List<DependentService> read() {
        return dependentServiceRepository.findAll();
    }


    @Override
    public DependentService update(int DS_code, DependentService updatedDependentService) {
        Optional<DependentService> existingDependentServiceOptional = dependentServiceRepository.findById(DS_code);

        if (existingDependentServiceOptional.isPresent()) {
            DependentService existingDependentService = existingDependentServiceOptional.get();
            existingDependentService.setName(updatedDependentService.getName());
            return dependentServiceRepository.save(existingDependentService);
        } else {
            throw new RuntimeException("Could not find Group Dimension with ID: " + DS_code);
        }
    }


    @Override
    public String delete(int DS_code) {
        dependentServiceRepository.deleteById(DS_code);
        return ("Group Dimension was successfully deleted");
    }

    @Override
    public DependentService findById(int DS_code) {
        Optional<DependentService> optionalDependentService = dependentServiceRepository.findById(DS_code);
        return optionalDependentService.orElseThrow(() -> new RuntimeException("DependentService with ID " + DS_code + " not found"));
    }


    @Override
    public List<DependentService> searchByKeyword(String name) {
        return dependentServiceRepository.searchByKeyword(name);
    }


}
