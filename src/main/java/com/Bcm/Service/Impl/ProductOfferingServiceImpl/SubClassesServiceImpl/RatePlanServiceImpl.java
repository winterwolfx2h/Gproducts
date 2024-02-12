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

    @Override
    public RatePlan create(RatePlan ratePlan) {
        try {
            return ratePlanRepository.save(ratePlan);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating RatePlan");
        }
    }

    @Override
    public List<RatePlan> read() {
        try {
            return ratePlanRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving RatePlans");
        }
    }

    @Override
    public RatePlan update(int po_RatePlanCode, RatePlan updatedRatePlan) {
        try {
            Optional<RatePlan> existingRatePlanOptional = ratePlanRepository.findById(po_RatePlanCode);
            if (existingRatePlanOptional.isPresent()) {
                RatePlan existingRatePlan = existingRatePlanOptional.get();
                existingRatePlan.setName(updatedRatePlan.getName());
                return ratePlanRepository.save(existingRatePlan);
            } else {
                throw new RuntimeException("Rate Plan with ID " + po_RatePlanCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating RatePlan");
        }
    }

    @Override
    public String delete(int po_RatePlanCode) {
        try {
            ratePlanRepository.deleteById(po_RatePlanCode);
            return "Rate Plan with ID " + po_RatePlanCode + " was successfully deleted";
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting RatePlan");
        }
    }

    @Override
    public RatePlan findById(int po_RatePlanCode) {
        try {
            Optional<RatePlan> optionalRatePlan = ratePlanRepository.findById(po_RatePlanCode);
            return optionalRatePlan.orElseThrow(() -> new RuntimeException("Rate Plan with ID " + po_RatePlanCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding RatePlan");
        }
    }

    @Override
    public List<RatePlan> searchByKeyword(String name) {
        try {
            return ratePlanRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching RatePlan by keyword");
        }
    }
}
