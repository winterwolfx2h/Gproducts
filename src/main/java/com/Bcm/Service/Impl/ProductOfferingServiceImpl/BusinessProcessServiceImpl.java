package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

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
    public BusinessProcess update(int businessProcessId, BusinessProcess updatedBusinessProcess) {
        Optional<BusinessProcess> existingBusinessProcessOptional = businessProcessRepository.findById(businessProcessId);

        if (existingBusinessProcessOptional.isPresent()) {
            BusinessProcess existingBusinessProcess = existingBusinessProcessOptional.get();
            existingBusinessProcess.setBussinessProcType(updatedBusinessProcess.getBussinessProcType());

            return businessProcessRepository.save(existingBusinessProcess);
        } else {
            throw new RuntimeException("Could not find BusinessProcess with ID: " + businessProcessId);
        }
    }

    @Override
    public String delete(int businessProcessId) {
        businessProcessRepository.deleteById(businessProcessId);
        return ("BusinessProcess was successfully deleted");
    }

    @Override
    public BusinessProcess findById(int businessProcessId) {
        Optional<BusinessProcess> optionalPlan = businessProcessRepository.findById(businessProcessId);
        return optionalPlan.orElseThrow(() -> new RuntimeException("BusinessProcess with ID " + businessProcessId + " not found"));
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

    @Override
    public boolean existsById(int businessProcessId) {
        return businessProcessRepository.existsById(businessProcessId);
    }
}

