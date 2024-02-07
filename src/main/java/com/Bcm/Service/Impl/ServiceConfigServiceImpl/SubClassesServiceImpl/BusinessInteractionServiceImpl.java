package com.Bcm.Service.Impl.ServiceConfigServiceImpl.SubClassesServiceImpl;


import com.Bcm.Model.ServiceABE.SubClasses.BusinessInteraction;
import com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo.BusinessInteractionRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.BusinessInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessInteractionServiceImpl implements BusinessInteractionService {


    @Autowired
    BusinessInteractionRepository businessInteractionRepository;

    @Autowired
    BusinessInteractionService businessInteractionService;

    @Override
    public BusinessInteraction create(BusinessInteraction BusinessInteraction) {
        return businessInteractionRepository.save(BusinessInteraction);
    }

    @Override
    public List<BusinessInteraction> read() {
        return businessInteractionRepository.findAll();
    }


    @Override
    public BusinessInteraction update(int BI_code, BusinessInteraction updatedBusinessInteraction) {
        Optional<BusinessInteraction> existingBusinessInteractionOptional = businessInteractionRepository.findById(BI_code);

        if (existingBusinessInteractionOptional.isPresent()) {
            BusinessInteraction existingBusinessInteraction = existingBusinessInteractionOptional.get();
            existingBusinessInteraction.setName(updatedBusinessInteraction.getName());
            return businessInteractionRepository.save(existingBusinessInteraction);
        } else {
            throw new RuntimeException("Could not find Group Dimension with ID: " + BI_code);
        }
    }


    @Override
    public String delete(int BI_code) {
        businessInteractionRepository.deleteById(BI_code);
        return ("Group Dimension was successfully deleted");
    }

    @Override
    public BusinessInteraction findById(int BI_code) {
        Optional<BusinessInteraction> optionalBusinessInteraction = businessInteractionRepository.findById(BI_code);
        return optionalBusinessInteraction.orElseThrow(() -> new RuntimeException("BusinessInteraction with ID " + BI_code + " not found"));
    }


    @Override
    public List<BusinessInteraction> searchByKeyword(String name) {
        return businessInteractionRepository.searchByKeyword(name);
    }


}
