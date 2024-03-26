package com.Bcm.Service.Impl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Repository.ProductOfferingRepo.POPlanRepository;
import com.Bcm.Service.Srvc.POPlanService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
public class POPlanServiceImpl implements POPlanService {

    final
    POPlanRepository popRepository;

    public POPlanServiceImpl(POPlanRepository poPlanRepository) {
        this.popRepository = poPlanRepository;
    }

    @Override
    public POPlan create(POPlan poPlan) {
        validateNotNullFields(poPlan);
        try {
            poPlan.setStatus("SUSPENDED");
            return popRepository.save(poPlan);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating POPlan", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating POPlan", e);
        }
    }

    @Override
    public List<POPlan> read() {
        try {
            return popRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading POPlans", e);
        }
    }

    @Override
    public POPlan update(int TMCODE, POPlan poPlan) {
        Optional<POPlan> existingPlanOptional = popRepository.findById(TMCODE);

        if (existingPlanOptional.isPresent()) {
            POPlan existingPlan = existingPlanOptional.get();
            existingPlan.setDES(poPlan.getDES());
            existingPlan.setSHDES(poPlan.getSHDES());
            existingPlan.setMarket(poPlan.getMarket());
            existingPlan.setSubMarket(poPlan.getSubMarket());
            existingPlan.setStatus(poPlan.getStatus());

            validateNotNullFields(existingPlan);

            try {
                return popRepository.save(existingPlan);
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
        if (poPlan.getDES() == null || poPlan.getSHDES() == null) {
            throw new InvalidInputException("DES, description, and parent cannot be null");
        }
    }

    @Override
    public String delete(int TMCODE) {
        if (!popRepository.existsById(TMCODE)) {
            throw new ResourceNotFoundException("POPlan with ID " + TMCODE + " not found");
        }

        try {
            popRepository.deleteById(TMCODE);
            return "POPlan with ID " + TMCODE + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting POPlan with ID: " + TMCODE, e);
        }
    }

    @Override
    public POPlan findById(int TMCODE) {
        try {
            return popRepository.findById(TMCODE)
                    .orElseThrow(() -> new ResourceNotFoundException("POPlan with ID " + TMCODE + " not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("POPlan with ID \"" + TMCODE + "\" not found", e);
        }
    }

    @Override
    public List<POPlan> searchByKeyword(String DES) {
        try {
            return popRepository.searchByKeyword(DES);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while searching for POPlans by keyword: " + DES, e);
        }
    }

    @Override
    public POPlan findBySHDES(String SHDES) {
        try {
            Optional<POPlan> optionalPOPlan = popRepository.findBySHDES(SHDES);
            return optionalPOPlan.orElseThrow(() -> new RuntimeException("POPlan with SHDES: " + SHDES + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding POPlan");
        }
    }

    @Override
    public POPlan changePoplanStatus(int TMCODE) {
        try {
            POPlan existingPlan = findById(TMCODE);

            if (existingPlan.getStatus().equals("Suspendu")) {
                existingPlan.setStatus("Actif");
            } else {
                existingPlan.setStatus("Suspendu");
            }

            return popRepository.save(existingPlan);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while changing POPlan status", e);
        }
    }

    @Override
    public boolean existsById(int TMCODE) {
        return popRepository.existsById(TMCODE);
    }
}
