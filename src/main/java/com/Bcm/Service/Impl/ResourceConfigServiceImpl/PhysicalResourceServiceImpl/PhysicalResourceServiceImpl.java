package com.Bcm.Service.Impl.ResourceConfigServiceImpl.PhysicalResourceServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InputException;
import com.Bcm.Exception.InvalidInput;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalResource;
import com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo.PhysicalResourceRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.PhysicalResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
public class PhysicalResourceServiceImpl implements PhysicalResourceService {

    @Autowired
    PhysicalResourceRepository PhysicalResourceRepository;

    @Override
    public PhysicalResource create(PhysicalResource PhysicalResource) {
        try {
            return PhysicalResourceRepository.save(PhysicalResource);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating product offering", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating product offering", e);
        }
    }

    @Override
    public List<PhysicalResource> read() {
        try {
            return PhysicalResourceRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading product offerings", e);
        }
    }

    @Override
    public PhysicalResource update(int LRID, PhysicalResource updatedPhysicalResource) {
        Optional<PhysicalResource> existingProductOptional = PhysicalResourceRepository.findById(LRID);

        if (existingProductOptional.isPresent()) {
            PhysicalResource existingProduct = existingProductOptional.get();
            existingProduct.setManufactureDate(updatedPhysicalResource.getManufactureDate());
            existingProduct.setName(updatedPhysicalResource.getName());
            existingProduct.setWeightUnits(updatedPhysicalResource.getWeightUnits());
            existingProduct.setSerialNumber(updatedPhysicalResource.getSerialNumber());

            try {
                if (updatedPhysicalResource.getName() == null || updatedPhysicalResource.getManufactureDate() == null) {
                    throw new InputException("Name and description cannot be null");
                }
                return PhysicalResourceRepository.save(existingProduct);
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseOperationException("Error updating product offering with ID: " + LRID, e);
            } catch (ObjectOptimisticLockingFailureException e) {
                throw new ConcurrentModificationException("Another user has modified the product offering with ID: " + LRID + ". Please try again.");
            } catch (InputException e) {
                throw e;
            } catch (InvalidInput e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("An unexpected error occurred while updating product offering with ID: " + LRID, e);
            }
        } else {
            throw new ResourceNotFoundException("Could not find product offering with ID: " + LRID);
        }
    }

    @Override
    public String delete(int LRID) {
        if (!PhysicalResourceRepository.existsById(LRID)) {
            throw new ResourceNotFoundException("Product offering with ID " + LRID + " not found");
        }

        try {
            PhysicalResourceRepository.deleteById(LRID);
            return "Product offering with ID " + LRID + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting product offering with ID: " + LRID, e);
        }
    }

    @Override
    public PhysicalResource findById(int LRID) {
        try {
            return PhysicalResourceRepository.findById(LRID)
                    .orElseThrow(() -> new ResourceNotFoundException(" An unexpected error occurred while finding product offering with ID:" + LRID));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Product offering with ID \"" + LRID + "\" not found", e);
        }
    }
}