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
    public POPlan update(int PO_ID, POPlan poPlan) {
        Optional<POPlan> existingPlanOptional = poPlanRepository.findById(PO_ID);

        if (existingPlanOptional.isPresent()) {
            POPlan existingPlan = existingPlanOptional.get();
            existingPlan.setName(poPlan.getName());
            existingPlan.setDescription(poPlan.getDescription());
            existingPlan.setParent(poPlan.getParent());

            validateNotNullFields(existingPlan);

            try {
                return poPlanRepository.save(existingPlan);
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseOperationException("Error updating POPlan with ID: " + PO_ID, e);
            } catch (ObjectOptimisticLockingFailureException e) {
                throw new ConcurrentModificationException("Another user has modified the POPlan with ID: " + PO_ID + ". Please try again.");
            } catch (Exception e) {
                throw new RuntimeException("An unexpected error occurred while updating POPlan with ID: " + PO_ID, e);
            }
        } else {
            throw new ResourceNotFoundException("Could not find POPlan with ID: " + PO_ID);
        }
    }

    private void validateNotNullFields(POPlan poPlan) {
        if (poPlan.getName() == null || poPlan.getDescription() == null || poPlan.getParent() == null) {
            throw new InvalidInputException("Name, description, and parent cannot be null");
        }
    }

    @Override
    public String delete(int PO_ID) {
        if (!poPlanRepository.existsById(PO_ID)) {
            throw new ResourceNotFoundException("POPlan with ID " + PO_ID + " not found");
        }

        try {
            poPlanRepository.deleteById(PO_ID);
            return "POPlan with ID " + PO_ID + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting POPlan with ID: " + PO_ID, e);
        }
    }

    @Override
    public POPlan findById(int PO_ID) {
        try {
            return poPlanRepository.findById(PO_ID)
                    .orElseThrow(() -> new ResourceNotFoundException("POPlan with ID " + PO_ID + " not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("POPlan with ID \"" + PO_ID + "\" not found", e);
        }
    }

    @Override
    public List<POPlan> searchByKeyword(String name) {
        try {
            return poPlanRepository.searchByKeyword(name);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while searching for POPlans by keyword: " + name, e);
        }
    }
}
