package com.Bcm.Service.Impl;

import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Repository.ProductOfferingRepo.POPlanRepository;
import com.Bcm.Service.Srvc.POPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class POPlanServiceImpl implements POPlanService {

    @Autowired
    POPlanRepository poPlanRepository;

    @Autowired
    POPlanService poPlanService;

    @Override
    public POPlan create(POPlan poPlan) {
        return poPlanRepository.save(poPlan);
    }

    @Override
    public List<POPlan> read() {
        return poPlanRepository.findAll();
    }

    @Override
    public POPlan update(int PO_ID, POPlan poPlan) {
        Optional<POPlan> existingPlanOptional = poPlanRepository.findById(PO_ID);

        if (existingPlanOptional.isPresent()) {
            POPlan existingPlan = existingPlanOptional.get();
            existingPlan.setName(poPlan.getName());
            existingPlan.setDescription(poPlan.getDescription());
            existingPlan.setParent(poPlan.getParent());
            return poPlanRepository.save(existingPlan);
        } else {
            throw new RuntimeException("Could not find plan with ID: " + PO_ID);
        }
    }


    @Override
    public String delete(int PO_ID) {
        poPlanRepository.deleteById(PO_ID);
        return ("plan was successfully deleted");
    }
    @Override
    public POPlan findById(int PO_ID) {
        Optional<POPlan> optionalPlan = poPlanRepository.findById(PO_ID);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Plan with ID " + PO_ID + " not found"));
    }


    @Override
    public List<POPlan> searchByKeyword(String name) {
        return poPlanRepository.searchByKeyword(name);
    }


}
