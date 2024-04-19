package com.Bcm.Service.Impl.ProductResourceServiceImpl;

import com.Bcm.Model.ProductResourceABE.LogicalResource;
import com.Bcm.Repository.ProductResourceRepository.LogicalResourceRepository;
import com.Bcm.Service.Srvc.ProductResourceSrvc.LogicalResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogicalResourceServiceImpl implements LogicalResourceService {

    @Autowired
    LogicalResourceRepository logicalResourceRepository;

    @Override
    public LogicalResource create(LogicalResource LogicalResource) {
        return logicalResourceRepository.save(LogicalResource);
    }

    @Override
    public List<LogicalResource> read() {
        return logicalResourceRepository.findAll();
    }

    @Override
    public LogicalResource update(int logResourceId, LogicalResource updatedLogicalResource) {
        Optional<LogicalResource> existingProductOptional = logicalResourceRepository.findById(logResourceId);

        if (existingProductOptional.isPresent()) {
            LogicalResource existingProduct = existingProductOptional.get();
            existingProduct.setLogicalResourceType(updatedLogicalResource.getLogicalResourceType());
            existingProduct.setLogicalResourceFormat(updatedLogicalResource.getLogicalResourceFormat());
            existingProduct.setServiceId(updatedLogicalResource.getServiceId());

            return logicalResourceRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find LogicalResource with ID: " + logResourceId);
        }
    }

    @Override
    public String delete(int logResourceId) {
        logicalResourceRepository.deleteById(logResourceId);
        return ("LogicalResource was successfully deleted");
    }

    @Override
    public LogicalResource findById(int logResourceId) {
        Optional<LogicalResource> optionalPlan = logicalResourceRepository.findById(logResourceId);
        return optionalPlan.orElseThrow(() -> new RuntimeException("LogicalResource with ID " + logResourceId + " not found"));
    }

    @Override
    public List<LogicalResource> searchByKeyword(String logicalResourceType) {
        return logicalResourceRepository.searchByKeyword(logicalResourceType);
    }

    @Override
    public LogicalResource findByLogicalResourceType(String logicalResourceType) {
        try {
            Optional<LogicalResource> optionalLogicalResource = logicalResourceRepository.findByLogicalResourceType(logicalResourceType);
            return optionalLogicalResource.orElseThrow(() -> new RuntimeException("LogicalResource with type " + logicalResourceType + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding LogicalResource");
        }
    }

    @Override
    public boolean existsById(int logResourceId) {
        return logicalResourceRepository.existsById(logResourceId);
    }
}

