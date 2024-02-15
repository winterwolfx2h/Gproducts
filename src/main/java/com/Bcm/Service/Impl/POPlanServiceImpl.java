package com.Bcm.Service.Impl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Repository.ProductOfferingRepo.POPlanRepository;
import com.Bcm.Service.Srvc.POPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
public class POPlanServiceImpl implements POPlanService {

    @Autowired
    POPlanRepository poPlanRepository;

    @Override
    public POPlan create(POPlan poPlan) {
        validateNotNullFields(poPlan);
        try {
            return poPlanRepository.save(poPlan);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating POPlan", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating POPlan", e);
        }
    }

    @Override
    public List<POPlan> read() {
        try {
            return poPlanRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading POPlans", e);
        }
    }

    @Override
    public POPlan update(int TMCODE, POPlan poPlan) {
        Optional<POPlan> existingPlanOptional = poPlanRepository.findById(TMCODE);

        if (existingPlanOptional.isPresent()) {
            POPlan existingPlan = existingPlanOptional.get();
            existingPlan.setDES(poPlan.getDES());
            existingPlan.setSHDES(poPlan.getSHDES());
            existingPlan.setParent(poPlan.getParent());

            validateNotNullFields(existingPlan);

            try {
                return poPlanRepository.save(existingPlan);
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseOperationException("Error updating POPlan with ID: " + TMCODE, e);
            } catch (ObjectOptimisticLockingFailureException e) {
                throw new ConcurrentModificationException("Another user has modified the POPlan with ID: " + TMCODE + ". Please try again.");
            } catch (Exception e) {
                throw new RuntimeException("An unexpected error occurred while updating POPlan with ID: " + TMCODE, e);
            }
        } else {
            throw new ResourceNotFoundException("Could not find POPlan with ID: " + TMCODE);
        }
    }

    private void validateNotNullFields(POPlan poPlan) {
        if (poPlan.getDES() == null || poPlan.getSHDES() == null || poPlan.getParent() == null) {
            throw new InvalidInputException("DES, description, and parent cannot be null");
        }
    }

    @Override
    public String delete(int TMCODE) {
        if (!poPlanRepository.existsById(TMCODE)) {
            throw new ResourceNotFoundException("POPlan with ID " + TMCODE + " not found");
        }

        try {
            poPlanRepository.deleteById(TMCODE);
            return "POPlan with ID " + TMCODE + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting POPlan with ID: " + TMCODE, e);
        }
    }

    @Override
    public POPlan findById(int TMCODE) {
        try {
            return poPlanRepository.findById(TMCODE)
                    .orElseThrow(() -> new ResourceNotFoundException("POPlan with ID " + TMCODE + " not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("POPlan with ID \"" + TMCODE + "\" not found", e);
        }
    }

    @Override
    public List<POPlan> searchByKeyword(String DES) {
        try {
            return poPlanRepository.searchByKeyword(DES);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while searching for POPlans by keyword: " + DES, e);
        }
    }
}
