package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.RatePlan;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.RatePlanRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.RatePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatePlanServiceImpl implements RatePlanService {


    @Autowired
    RatePlanRepository ratePlanRepository;

    @Autowired
    RatePlanService ratePlanService;

    @Override
    public RatePlan create(RatePlan RatePlan) {
        return ratePlanRepository.save(RatePlan);
    }

    @Override
    public List<RatePlan> read() {
        return ratePlanRepository.findAll();
    }

    @Override
    public RatePlan update(int po_RatePlanCode, RatePlan updatedRatePlan) {
        Optional<RatePlan> existingRatePlanOptional = ratePlanRepository.findById(po_RatePlanCode);

        if (existingRatePlanOptional.isPresent()) {
            RatePlan existingRatePlan = existingRatePlanOptional.get();
            existingRatePlan.setName(updatedRatePlan.getName());
            return ratePlanRepository.save(existingRatePlan);
        } else {
            throw new RuntimeException("Could not find Rate Plan with ID: " + po_RatePlanCode);
        }
    }

    @Override
    public String delete(int po_RatePlanCode) {
        ratePlanRepository.deleteById(po_RatePlanCode);
        return ("Rate Plan was successfully deleted");
    }

    @Override
    public RatePlan findById(int po_RatePlanCode) {
        Optional<RatePlan> optionalPlan = ratePlanRepository.findById(po_RatePlanCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Rate Plan with ID " + po_RatePlanCode + " not found"));
    }


    @Override
    public List<RatePlan> searchByKeyword(String name) {
        return ratePlanRepository.searchByKeyword(name);
    }


}
