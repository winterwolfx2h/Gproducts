package com.Bcm.Service.Impl.BusinessProcessOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Repository.ProductOfferingRepo.BusinessProcessRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BusinessProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BusinessProcessServiceImpl implements BusinessProcessService {

    @Autowired
    BusinessProcessRepository businessProcessRepository;

    @Override
    public BusinessProcess create(BusinessProcess BusinessProcess) {
        return businessProcessRepository.save(BusinessProcess);
    }

    @Override
    public List<BusinessProcess> read() {
        return businessProcessRepository.findAll();
    }

    @Override
    public BusinessProcess update(int PrResId, BusinessProcess updatedBusinessProcess) {
        Optional<BusinessProcess> existingBusinessProcessOptional = businessProcessRepository.findById(PrResId);

        if (existingBusinessProcessOptional.isPresent()) {
            BusinessProcess existingBusinessProcess = existingBusinessProcessOptional.get();
            existingBusinessProcess.setBussinessProcType(updatedBusinessProcess.getBussinessProcType());

            return businessProcessRepository.save(existingBusinessProcess);
        } else {
            throw new RuntimeException("Could not find BusinessProcess with ID: " + PrResId);
        }
    }

    @Override
    public String delete(int PrResId) {
        businessProcessRepository.deleteById(PrResId);
        return ("BusinessProcess was successfully deleted");
    }

    @Override
    public BusinessProcess findById(int PrResId) {
        Optional<BusinessProcess> optionalPlan = businessProcessRepository.findById(PrResId);
        return optionalPlan.orElseThrow(() -> new RuntimeException("BusinessProcess with ID " + PrResId + " not found"));
    }

    @Override
    public List<BusinessProcess> searchByKeyword(String bussinessProcType) {
        return businessProcessRepository.searchByKeyword(bussinessProcType);
    }

    @Override
    public BusinessProcess findByBussinessProcType(String bussinessProcType) {
        try {
            Optional<BusinessProcess> optionalBusinessProcess = businessProcessRepository.findByBussinessProcType(bussinessProcType);
            return optionalBusinessProcess.orElseThrow(() -> new RuntimeException("BusinessProcess with " + bussinessProcType + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding BusinessProcess");
        }
    }
}

