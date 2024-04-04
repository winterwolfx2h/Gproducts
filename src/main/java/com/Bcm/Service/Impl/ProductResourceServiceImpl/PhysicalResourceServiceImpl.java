package com.Bcm.Service.Impl.ProductResourceServiceImpl;

import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.Bcm.Repository.ProductResourceRepository.PhysicalResourceRepository;
import com.Bcm.Service.Srvc.ProductResourceSrvc.PhysicalResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhysicalResourceServiceImpl implements PhysicalResourceService {

    @Autowired
    PhysicalResourceRepository physicalResourceRepository;

    @Override
    public PhysicalResource create(PhysicalResource PhysicalResource) {
        return physicalResourceRepository.save(PhysicalResource);
    }

    @Override
    public List<PhysicalResource> read() {
        return physicalResourceRepository.findAll();
    }

    @Override
    public PhysicalResource update(int phyResourceId, PhysicalResource updatedPhysicalResource) {
        Optional<PhysicalResource> existingProductOptional = physicalResourceRepository.findById(phyResourceId);

        if (existingProductOptional.isPresent()) {
            PhysicalResource existingProduct = existingProductOptional.get();
            existingProduct.setPhysicalResourceType(updatedPhysicalResource.getPhysicalResourceType());
            existingProduct.setPhysicalResourceFromat(updatedPhysicalResource.getPhysicalResourceFromat());
            existingProduct.setServiceId(updatedPhysicalResource.getServiceId());

            return physicalResourceRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find PhysicalResource with ID: " + phyResourceId);
        }
    }

    @Override
    public String delete(int phyResourceId) {
        physicalResourceRepository.deleteById(phyResourceId);
        return ("PhysicalResource was successfully deleted");
    }

    @Override
    public PhysicalResource findById(int phyResourceId) {
        Optional<PhysicalResource> optionalPlan = physicalResourceRepository.findById(phyResourceId);
        return optionalPlan.orElseThrow(() -> new RuntimeException("PhysicalResource with ID " + phyResourceId + " not found"));
    }

    @Override
    public List<PhysicalResource> searchByKeyword(String physicalResourceType) {
        return physicalResourceRepository.searchByKeyword(physicalResourceType);
    }

    @Override
    public PhysicalResource findByPhysicalResourceType(String physicalResourceType) {
        try {
            Optional<PhysicalResource> optionalPhysicalResource = physicalResourceRepository.findByPhysicalResourceType(physicalResourceType);
            return optionalPhysicalResource.orElseThrow(() -> new RuntimeException("PhysicalResource with type " + physicalResourceType + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding PhysicalResource");
        }
    }

    @Override
    public boolean existsById(int phyResourceId) {
        return physicalResourceRepository.existsById(phyResourceId);
    }
}

