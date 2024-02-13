package com.Bcm.Service.Impl.ResourceConfigServiceImpl.LogicalResourceServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InputException;
import com.Bcm.Exception.InvalidInput;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.LogicalResource;
import com.Bcm.Repository.ResourceSpecRepo.LogicalResourceRepo.LogicalResourceRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceService.LogicalResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
@Service
public class LogicalResourceServiceImpl implements LogicalResourceService {

    @Autowired
    LogicalResourceRepository logicalResourceRepository;
    @Override
    public LogicalResource create(LogicalResource LogicalResource) {
        try {
            return logicalResourceRepository.save(LogicalResource);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating product offering", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating product offering", e);
        }
    }
    @Override
    public List<LogicalResource> read() {
        try {
            return logicalResourceRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading product offerings", e);
        }
    }
    @Override
    public LogicalResource update(int LRID, LogicalResource updatedLogicalResource) {
        Optional<LogicalResource> existingProductOptional = logicalResourceRepository.findById(LRID);

        if (existingProductOptional.isPresent()) {
            LogicalResource existingProduct = existingProductOptional.get();
            existingProduct.setValidFor(updatedLogicalResource.getValidFor());
            existingProduct.setStatus(updatedLogicalResource.getStatus());

            try {
                if (updatedLogicalResource.getValidFor() == null ||updatedLogicalResource.getStatus() == null) {
                    throw new InputException("Name and description cannot be null");
                }
                return logicalResourceRepository.save(existingProduct);
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
        if (!logicalResourceRepository.existsById(LRID)) {
            throw new ResourceNotFoundException("Product offering with ID " + LRID + " not found");
        }

        try {
            logicalResourceRepository.deleteById(LRID);
            return "Product offering with ID " + LRID + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting product offering with ID: " + LRID, e);
        }
    }
    @Override
    public LogicalResource findById(int LRID) {
        try {
            return logicalResourceRepository.findById(LRID)
                    .orElseThrow(() -> new ResourceNotFoundException(" An unexpected error occurred while finding product offering with ID:" + LRID));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Product offering with ID \"" + LRID + "\" not found", e);
        }
    }
}