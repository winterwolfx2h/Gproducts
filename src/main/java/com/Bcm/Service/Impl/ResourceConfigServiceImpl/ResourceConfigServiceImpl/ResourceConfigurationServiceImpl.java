package com.Bcm.Service.Impl.ResourceConfigServiceImpl.ResourceConfigServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InputException;
import com.Bcm.Exception.InvalidInput;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.ResourceConfiguration;
import com.Bcm.Repository.ResourceSpecRepo.ResourceConfigRepo.ResourceConfigurationRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.ResourceConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceConfigurationServiceImpl implements ResourceConfigurationService {

    @Autowired
    ResourceConfigurationRepository ResourceConfigurationRepository;
    @Override
    public ResourceConfiguration create(ResourceConfiguration ResourceConfiguration) {
        try {
            return ResourceConfigurationRepository.save(ResourceConfiguration);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating product offering", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating product offering", e);
        }
    }
    @Override
    public List<ResourceConfiguration> read() {
        try {
            return ResourceConfigurationRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading product offerings", e);
        }
    }
    @Override
    public ResourceConfiguration update(int LRID, ResourceConfiguration updatedResourceConfiguration) {
        Optional<ResourceConfiguration> existingProductOptional = ResourceConfigurationRepository.findById(LRID);

        if (existingProductOptional.isPresent()) {
            ResourceConfiguration existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedResourceConfiguration.getName());
            existingProduct.setValidFor(updatedResourceConfiguration.getValidFor());
            existingProduct.setVersion(updatedResourceConfiguration.getVersion());
            existingProduct.setDateCreated(updatedResourceConfiguration.getDateCreated());
            existingProduct.setDescription(updatedResourceConfiguration.getDescription());

            try {
                if (updatedResourceConfiguration.getName() == null ||updatedResourceConfiguration.getDescription() == null) {
                    throw new InputException("Name and description cannot be null");
                }
                return ResourceConfigurationRepository.save(existingProduct);
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
        if (!ResourceConfigurationRepository.existsById(LRID)) {
            throw new ResourceNotFoundException("Product offering with ID " + LRID + " not found");
        }

        try {
            ResourceConfigurationRepository.deleteById(LRID);
            return "Product offering with ID " + LRID + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting product offering with ID: " + LRID, e);
        }
    }
    @Override
    public ResourceConfiguration findById(int LRID) {
        try {
            return ResourceConfigurationRepository.findById(LRID)
                    .orElseThrow(() -> new ResourceNotFoundException(" An unexpected error occurred while finding product offering with ID:" + LRID));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Product offering with ID \"" + LRID + "\" not found", e);
        }
    }
}