package com.Bcm.Service.Impl.ResourceConfigServiceImpl.PhysicalResourceServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InputException;
import com.Bcm.Exception.InvalidInput;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalPort;
import com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo.PhysicalPortRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.PhysicalPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
public class PhysicalPortServiceImpl implements PhysicalPortService {

    @Autowired
    PhysicalPortRepository PhysicalPortRepository;

    @Override
    public PhysicalPort create(PhysicalPort PhysicalPort) {
        try {
            return PhysicalPortRepository.save(PhysicalPort);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating product offering", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating product offering", e);
        }
    }

    @Override
    public List<PhysicalPort> read() {
        try {
            return PhysicalPortRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading product offerings", e);
        }
    }

    @Override
    public PhysicalPort update(int LRID, PhysicalPort updatedPhysicalPort) {
        Optional<PhysicalPort> existingProductOptional = PhysicalPortRepository.findById(LRID);

        if (existingProductOptional.isPresent()) {
            PhysicalPort existingProduct = existingProductOptional.get();
            existingProduct.setPortType(updatedPhysicalPort.getPortType());
            existingProduct.setIfType(updatedPhysicalPort.getIfType());
            existingProduct.setPortNumber(updatedPhysicalPort.getPortNumber());
            existingProduct.setDuplexMode(updatedPhysicalPort.getDuplexMode());

            try {
                if (updatedPhysicalPort.getPortType() == null || updatedPhysicalPort.getDuplexMode() == null) {
                    throw new InputException("Name and description cannot be null");
                }
                return PhysicalPortRepository.save(existingProduct);
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
        if (!PhysicalPortRepository.existsById(LRID)) {
            throw new ResourceNotFoundException("Product offering with ID " + LRID + " not found");
        }

        try {
            PhysicalPortRepository.deleteById(LRID);
            return "Product offering with ID " + LRID + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting product offering with ID: " + LRID, e);
        }
    }

    @Override
    public PhysicalPort findById(int LRID) {
        try {
            return PhysicalPortRepository.findById(LRID)
                    .orElseThrow(() -> new ResourceNotFoundException(" An unexpected error occurred while finding product offering with ID:" + LRID));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Product offering with ID \"" + LRID + "\" not found", e);
        }
    }
}