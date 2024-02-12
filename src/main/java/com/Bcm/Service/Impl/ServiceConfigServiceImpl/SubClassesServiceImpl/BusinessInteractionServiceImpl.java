package com.Bcm.Service.Impl.ServiceConfigServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ServiceABE.SubClasses.BusinessInteraction;
import com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo.BusinessInteractionRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.BusinessInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessInteractionServiceImpl implements BusinessInteractionService {

    private final BusinessInteractionRepository businessInteractionRepository;

    @Autowired
    public BusinessInteractionServiceImpl(BusinessInteractionRepository businessInteractionRepository) {
        this.businessInteractionRepository = businessInteractionRepository;
    }

    @Override
    @Transactional
    public BusinessInteraction create(BusinessInteraction businessInteraction) {
        if (businessInteraction == null) {
            throw new IllegalArgumentException("BusinessInteraction cannot be null");
        }
        return businessInteractionRepository.save(businessInteraction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessInteraction> read() {
        return businessInteractionRepository.findAll();
    }

    @Override
    @Transactional
    public BusinessInteraction update(int BI_code, BusinessInteraction updatedBusinessInteraction) {
        if (updatedBusinessInteraction == null) {
            throw new IllegalArgumentException("Updated BusinessInteraction cannot be null");
        }
        Optional<BusinessInteraction> existingBusinessInteractionOptional = businessInteractionRepository.findById(BI_code);
        if (existingBusinessInteractionOptional.isPresent()) {
            BusinessInteraction existingBusinessInteraction = existingBusinessInteractionOptional.get();
            existingBusinessInteraction.setName(updatedBusinessInteraction.getName());
            return businessInteractionRepository.save(existingBusinessInteraction);
        } else {
            throw new RuntimeException("BusinessInteraction with ID " + BI_code + " not found");
        }
    }

    @Override
    @Transactional
    public String delete(int BI_code) {
        Optional<BusinessInteraction> businessInteractionOptional = businessInteractionRepository.findById(BI_code);
        if (businessInteractionOptional.isPresent()) {
            businessInteractionRepository.deleteById(BI_code);
            return "BusinessInteraction with ID " + BI_code + " was successfully deleted";
        } else {
            throw new RuntimeException("BusinessInteraction with ID " + BI_code + " not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BusinessInteraction findById(int BI_code) {
        Optional<BusinessInteraction> optionalBusinessInteraction = businessInteractionRepository.findById(BI_code);
        return optionalBusinessInteraction.orElseThrow(() -> new RuntimeException("BusinessInteraction with ID " + BI_code + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessInteraction> searchByKeyword(String name) {
        return businessInteractionRepository.searchByKeyword(name);
    }
}
