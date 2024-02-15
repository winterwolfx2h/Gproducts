package com.Bcm.Service.Impl.ResourceConfigServiceImpl.ResourceConfigServiceImpl.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ResourceConfigVersion;
import com.Bcm.Repository.ResourceSpecRepo.ResourceConfigRepo.SubClasses.ResourceConfigVersionRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.SubClasses.ResourceConfigVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceConfigVersionServiceImpl implements ResourceConfigVersionService {

    @Autowired
    ResourceConfigVersionRepository ResourceConfigVersionRepository;

    @Override
    public ResourceConfigVersion create(ResourceConfigVersion ResourceConfigVersion) {
        try {
            if (ResourceConfigVersion == null) {
                throw new IllegalArgumentException(" ResourceConfigVersion cannot be null");
            }
            return ResourceConfigVersionRepository.save(ResourceConfigVersion);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There was a Data integrity violation occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating  ResourceConfigVersion: " + e.getMessage());
        }
    }

    @Override
    public List<ResourceConfigVersion> read() {
        return ResourceConfigVersionRepository.findAll();
    }

    @Override
    public ResourceConfigVersion update(int FID, ResourceConfigVersion updatedResourceConfigVersion) {
        try {
            Optional<ResourceConfigVersion> existingResourceConfigVersionOptional = ResourceConfigVersionRepository.findById(FID);

            if (existingResourceConfigVersionOptional.isPresent()) {
                ResourceConfigVersion existingResourceConfigVersion = existingResourceConfigVersionOptional.get();
                existingResourceConfigVersion.setName(updatedResourceConfigVersion.getName());
                return ResourceConfigVersionRepository.save(existingResourceConfigVersion);
            } else {
                throw new RuntimeException("Could not find  ResourceConfigVersion with ID: " + FID);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid  ResourceConfigVersion Code: " + FID);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the  ResourceConfigVersion: " + ex.getMessage());
        }
    }

    @Override
    public String delete(int FID) {
        Optional<ResourceConfigVersion> optionalResourceConfigVersion = ResourceConfigVersionRepository.findById(FID);
        if (optionalResourceConfigVersion.isPresent()) {
            ResourceConfigVersionRepository.deleteById(FID);
            return (" ResourceConfigVersion was successfully deleted");
        } else {
            throw new RuntimeException("Could not find  ResourceConfigVersion with ID: " + FID);
        }
    }

    @Override
    public ResourceConfigVersion findById(int FID) {
        Optional<ResourceConfigVersion> optionalResourceConfigVersion = ResourceConfigVersionRepository.findById(FID);
        return optionalResourceConfigVersion.orElseThrow(() -> new RuntimeException(" ResourceConfigVersion with ID " + FID + " not found"));
    }

    @Override
    public List<ResourceConfigVersion> searchByKeyword(String name) {
        return ResourceConfigVersionRepository.searchByKeyword(name);
    }
}