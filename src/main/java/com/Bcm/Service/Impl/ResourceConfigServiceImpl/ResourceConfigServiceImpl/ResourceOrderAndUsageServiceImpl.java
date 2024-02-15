package com.Bcm.Service.Impl.ResourceConfigServiceImpl.ResourceConfigServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InputException;
import com.Bcm.Exception.InvalidInput;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.ResourceOrderAndUsage;
import com.Bcm.Repository.ResourceSpecRepo.ResourceConfigRepo.ResourceOrderAndUsageRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.ResourceOrderAndUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceOrderAndUsageServiceImpl implements ResourceOrderAndUsageService {

    @Autowired
    ResourceOrderAndUsageRepository ResourceOrderAndUsageRepository;

    @Override
    public ResourceOrderAndUsage create(ResourceOrderAndUsage ResourceOrderAndUsage) {
        try {
            return ResourceOrderAndUsageRepository.save(ResourceOrderAndUsage);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating product offering", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating product offering", e);
        }
    }

    @Override
    public List<ResourceOrderAndUsage> read() {
        try {
            return ResourceOrderAndUsageRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading product offerings", e);
        }
    }

    @Override
    public ResourceOrderAndUsage update(int LRID, ResourceOrderAndUsage updatedResourceOrderAndUsage) {
        Optional<ResourceOrderAndUsage> existingProductOptional = ResourceOrderAndUsageRepository.findById(LRID);

        if (existingProductOptional.isPresent()) {
            ResourceOrderAndUsage existingProduct = existingProductOptional.get();
            existingProduct.setStartDate(updatedResourceOrderAndUsage.getStartDate());
            existingProduct.setEndDate(updatedResourceOrderAndUsage.getEndDate());
            existingProduct.setStatus(updatedResourceOrderAndUsage.getStatus());
            existingProduct.setAction(updatedResourceOrderAndUsage.getAction());
            existingProduct.setUsageStatus(updatedResourceOrderAndUsage.getUsageStatus());
            existingProduct.setUsageDate(updatedResourceOrderAndUsage.getUsageDate());

            try {
                if (updatedResourceOrderAndUsage.getStatus() == null || updatedResourceOrderAndUsage.getStartDate() == null) {
                    throw new InputException("Name and description cannot be null");
                }
                return ResourceOrderAndUsageRepository.save(existingProduct);
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
        if (!ResourceOrderAndUsageRepository.existsById(LRID)) {
            throw new ResourceNotFoundException("Product offering with ID " + LRID + " not found");
        }

        try {
            ResourceOrderAndUsageRepository.deleteById(LRID);
            return "Product offering with ID " + LRID + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting product offering with ID: " + LRID, e);
        }
    }

    @Override
    public ResourceOrderAndUsage findById(int LRID) {
        try {
            return ResourceOrderAndUsageRepository.findById(LRID)
                    .orElseThrow(() -> new ResourceNotFoundException(" An unexpected error occurred while finding product offering with ID:" + LRID));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Product offering with ID \"" + LRID + "\" not found", e);
        }
    }
}