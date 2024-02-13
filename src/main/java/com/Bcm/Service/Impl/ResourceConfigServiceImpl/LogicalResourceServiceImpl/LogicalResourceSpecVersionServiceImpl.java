package com.Bcm.Service.Impl.ResourceConfigServiceImpl.LogicalResourceServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InputException;
import com.Bcm.Exception.InvalidInput;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.LogicalResourceSpecVersion;
import com.Bcm.Repository.ResourceSpecRepo.LogicalResourceRepo.LogicalResourceSpecVersionRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceSpecVersionService.LogicalResourceSpecVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
public class LogicalResourceSpecVersionServiceImpl implements LogicalResourceSpecVersionService {

    @Autowired
    LogicalResourceSpecVersionRepository logicalResourceSpecVersionRepository;
    @Override
    public LogicalResourceSpecVersion create(LogicalResourceSpecVersion LogicalResourceSpecVersion) {
        try {
            return logicalResourceSpecVersionRepository.save(LogicalResourceSpecVersion);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating product offering", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating product offering", e);
        }
    }
    @Override
    public List<LogicalResourceSpecVersion> read() {
        try {
            return logicalResourceSpecVersionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading product offerings", e);
        }
    }
    @Override
    public LogicalResourceSpecVersion update(int LRID, LogicalResourceSpecVersion updatedLogicalResourceSpecVersion) {
        Optional<LogicalResourceSpecVersion> existingProductOptional = logicalResourceSpecVersionRepository.findById(LRID);

        if (existingProductOptional.isPresent()) {
            LogicalResourceSpecVersion existingProduct = existingProductOptional.get();
            existingProduct.setFormat(updatedLogicalResourceSpecVersion.getFormat());
            existingProduct.setNumber(updatedLogicalResourceSpecVersion.getNumber());
            existingProduct.setReason(updatedLogicalResourceSpecVersion.getReason());
            existingProduct.setTimestamp(updatedLogicalResourceSpecVersion.getTimestamp());

            try {
                if (updatedLogicalResourceSpecVersion.getFormat() == null ||updatedLogicalResourceSpecVersion.getReason() == null) {
                    throw new InputException("Name and description cannot be null");
                }
                return logicalResourceSpecVersionRepository.save(existingProduct);
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
        if (!logicalResourceSpecVersionRepository.existsById(LRID)) {
            throw new ResourceNotFoundException("Product offering with ID " + LRID + " not found");
        }

        try {
            logicalResourceSpecVersionRepository.deleteById(LRID);
            return "Product offering with ID " + LRID + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting product offering with ID: " + LRID, e);
        }
    }
    @Override
    public LogicalResourceSpecVersion findById(int LRID) {
        try {
            return logicalResourceSpecVersionRepository.findById(LRID)
                    .orElseThrow(() -> new ResourceNotFoundException(" An unexpected error occurred while finding product offering with ID:" + LRID));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Product offering with ID \"" + LRID + "\" not found", e);
        }
    }
}