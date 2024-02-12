package com.Bcm.Service.Impl.ServiceConfigServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ServiceABE.SubClasses.DependentService;
import com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo.DependentServiceRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.DependentServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DependentServiceServiceImpl implements DependentServiceService {

    private final DependentServiceRepository dependentServiceRepository;

    @Autowired
    public DependentServiceServiceImpl(DependentServiceRepository dependentServiceRepository) {
        this.dependentServiceRepository = dependentServiceRepository;
    }

    @Override
    @Transactional
    public DependentService create(DependentService dependentService) {
        if (dependentService == null) {
            throw new IllegalArgumentException("DependentService cannot be null");
        }
        return dependentServiceRepository.save(dependentService);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DependentService> read() {
        return dependentServiceRepository.findAll();
    }

    @Override
    @Transactional
    public DependentService update(int DS_code, DependentService updatedDependentService) {
        if (updatedDependentService == null) {
            throw new IllegalArgumentException("Updated DependentService cannot be null");
        }
        Optional<DependentService> existingDependentServiceOptional = dependentServiceRepository.findById(DS_code);
        if (existingDependentServiceOptional.isPresent()) {
            DependentService existingDependentService = existingDependentServiceOptional.get();
            existingDependentService.setName(updatedDependentService.getName());
            return dependentServiceRepository.save(existingDependentService);
        } else {
            throw new RuntimeException("DependentService with ID " + DS_code + " not found");
        }
    }

    @Override
    @Transactional
    public String delete(int DS_code) {
        Optional<DependentService> dependentServiceOptional = dependentServiceRepository.findById(DS_code);
        if (dependentServiceOptional.isPresent()) {
            dependentServiceRepository.deleteById(DS_code);
            return "DependentService with ID " + DS_code + " was successfully deleted";
        } else {
            throw new RuntimeException("DependentService with ID " + DS_code + " not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DependentService findById(int DS_code) {
        Optional<DependentService> optionalDependentService = dependentServiceRepository.findById(DS_code);
        return optionalDependentService.orElseThrow(() -> new RuntimeException("DependentService with ID " + DS_code + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DependentService> searchByKeyword(String name) {
        return dependentServiceRepository.searchByKeyword(name);
    }
}
