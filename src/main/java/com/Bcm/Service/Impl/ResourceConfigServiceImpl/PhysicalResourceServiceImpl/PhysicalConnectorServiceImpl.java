package com.Bcm.Service.Impl.ResourceConfigServiceImpl.PhysicalResourceServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InputException;
import com.Bcm.Exception.InvalidInput;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalConnector;
import com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo.PhysicalConnectorRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.PhysicalConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
public class PhysicalConnectorServiceImpl implements PhysicalConnectorService {

    @Autowired
    PhysicalConnectorRepository PhysicalConnectorRepository;

    @Override
    public PhysicalConnector create(PhysicalConnector PhysicalConnector) {
        try {
            return PhysicalConnectorRepository.save(PhysicalConnector);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating product offering", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating product offering", e);
        }
    }

    @Override
    public List<PhysicalConnector> read() {
        try {
            return PhysicalConnectorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading product offerings", e);
        }
    }

    @Override
    public PhysicalConnector update(int LRID, PhysicalConnector updatedPhysicalConnector) {
        Optional<PhysicalConnector> existingProductOptional = PhysicalConnectorRepository.findById(LRID);

        if (existingProductOptional.isPresent()) {
            PhysicalConnector existingProduct = existingProductOptional.get();
            existingProduct.setConnectorType(updatedPhysicalConnector.getConnectorType());
            existingProduct.setGender(updatedPhysicalConnector.getGender());
            existingProduct.setDescription(updatedPhysicalConnector.getDescription());
            existingProduct.setCableType(updatedPhysicalConnector.getCableType());

            try {
                if (updatedPhysicalConnector.getConnectorType() == null || updatedPhysicalConnector.getDescription() == null) {
                    throw new InputException("Name and description cannot be null");
                }
                return PhysicalConnectorRepository.save(existingProduct);
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
        if (!PhysicalConnectorRepository.existsById(LRID)) {
            throw new ResourceNotFoundException("Product offering with ID " + LRID + " not found");
        }

        try {
            PhysicalConnectorRepository.deleteById(LRID);
            return "Product offering with ID " + LRID + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting product offering with ID: " + LRID, e);
        }
    }

    @Override
    public PhysicalConnector findById(int LRID) {
        try {
            return PhysicalConnectorRepository.findById(LRID)
                    .orElseThrow(() -> new ResourceNotFoundException(" An unexpected error occurred while finding product offering with ID:" + LRID));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Product offering with ID \"" + LRID + "\" not found", e);
        }
    }
}