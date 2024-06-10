package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Repository.ProductOfferingRepo.BusinessProcessRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BusinessProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BusinessProcessServiceImpl implements BusinessProcessService {


    final BusinessProcessRepository businessProcessRepository;

    @Override
    public List<BusinessProcess> create(List<BusinessProcess> businessProcesses) {
        for (BusinessProcess businessProcess : businessProcesses) {
            if (businessProcessRepository.findByBussinessProcType(businessProcess.getBussinessProcType()).isPresent()) {
                throw new RuntimeException("BusinessProcess with type " + businessProcess.getBussinessProcType() + " already exists");
            }

            if (businessProcess.getBusinessProcess_id() != 0 && !businessProcessRepository.existsById(businessProcess.getBusinessProcess_id())) {
                throw new RuntimeException("BusinessProcess ID " + businessProcess.getBusinessProcess_id() + " does not exist");
            }
        }

        return businessProcessRepository.saveAll(businessProcesses);
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

