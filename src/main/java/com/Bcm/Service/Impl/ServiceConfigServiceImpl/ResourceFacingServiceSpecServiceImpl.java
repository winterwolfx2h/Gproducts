package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Exception.ServiceAlreadyExistsException;
import com.Bcm.Model.ProductResourceABE.LogicalResource;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Model.ServiceABE.ResourceFacingServiceSpec;
import com.Bcm.Repository.ProductResourceRepository.LogicalResourceRepository;
import com.Bcm.Repository.ServiceConfigRepo.CustomerFacingServiceSpecRepository;
import com.Bcm.Repository.ServiceConfigRepo.ResourceFacingServiceSpecRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ResourceFacingServiceSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceFacingServiceSpecServiceImpl implements ResourceFacingServiceSpecService {

    final ResourceFacingServiceSpecRepository resourceFacingServiceSpecRepository;
    final CustomerFacingServiceSpecRepository customerFacingServiceSpecRepository;
    final LogicalResourceRepository logicalResourceRepository;

    @Override
    public ResourceFacingServiceSpec create(ResourceFacingServiceSpec resourceFacingServiceSpec) {
        validateNotNullFields(resourceFacingServiceSpec);

        // Check if LogicalResource exists
        LogicalResource logicalResource =
                logicalResourceRepository
                        .findById(resourceFacingServiceSpec.getLogicalResource().getLR_id())
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "LogicalResource with ID "
                                                        + resourceFacingServiceSpec.getLogicalResource().getLR_id()
                                                        + " not found"));

        // Check if CustomerFacingServiceSpec exists
        CustomerFacingServiceSpec customerFacingServiceSpec =
                customerFacingServiceSpecRepository
                        .findById(resourceFacingServiceSpec.getCustomerFacingServiceSpec().getServiceId())
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "CustomerFacingServiceSpec with ID "
                                                        + resourceFacingServiceSpec.getCustomerFacingServiceSpec().getServiceId()
                                                        + " not found"));

        // Set the associated entities
        resourceFacingServiceSpec.setLogicalResource(logicalResource);
        resourceFacingServiceSpec.setCustomerFacingServiceSpec(customerFacingServiceSpec);

        try {
            return resourceFacingServiceSpecRepository.save(resourceFacingServiceSpec);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating Resource Facing Service Spec", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating Resource Facing Service Spec", e);
        }
    }

    @Override
    public List<ResourceFacingServiceSpec> read() {
        try {
            return resourceFacingServiceSpecRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading ResourceFacingServiceSpecs", e);
        }
    }

    @Override
    public ResourceFacingServiceSpec update(int Rfss_code, ResourceFacingServiceSpec updatedResourceFacingServiceSpec) {
        Optional<ResourceFacingServiceSpec> existingResourceFacingServiceSpecOptional =
                resourceFacingServiceSpecRepository.findById(Rfss_code);

        if (existingResourceFacingServiceSpecOptional.isPresent()) {
            ResourceFacingServiceSpec existingResourceFacingServiceSpec = existingResourceFacingServiceSpecOptional.get();
            if (!existingResourceFacingServiceSpec
                    .getExternalNPCode()
                    .equals(updatedResourceFacingServiceSpec.getExternalNPCode())) {
                if (resourceFacingServiceSpecRepository
                        .findByexternalNPCode(updatedResourceFacingServiceSpec.getExternalNPCode())
                        .isPresent()) {
                    throw new ServiceAlreadyExistsException("Resource with the same name already exists");
                }
            }
            existingResourceFacingServiceSpec.setExternalNPCode(updatedResourceFacingServiceSpec.getExternalNPCode());
            existingResourceFacingServiceSpec.setCustomerFacingServiceSpec(
                    updatedResourceFacingServiceSpec.getCustomerFacingServiceSpec());
            existingResourceFacingServiceSpec.setLogicalResource(updatedResourceFacingServiceSpec.getLogicalResource());

            return resourceFacingServiceSpecRepository.save(existingResourceFacingServiceSpec);
        } else {
            throw new ResourceNotFoundException("Could not find ResourceFacingServiceSpec with ID: " + Rfss_code);
        }
    }

    @Override
    public String delete(int Rfss_code) {
        if (!resourceFacingServiceSpecRepository.existsById(Rfss_code)) {
            throw new ResourceNotFoundException("ResourceFacingServiceSpec with ID " + Rfss_code + " not found");
        }

        try {
            resourceFacingServiceSpecRepository.deleteById(Rfss_code);
            return "ResourceFacingServiceSpec  with ID " + Rfss_code + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException(
                    "An unexpected error occurred while deleting ResourceFacingServiceSpec with ID: " + Rfss_code, e);
        }
    }

    @Override
    public ResourceFacingServiceSpec findById(int Rfss_code) {
        try {
            return resourceFacingServiceSpecRepository
                    .findById(Rfss_code)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("ResourceFacingServiceSpec  with ID " + Rfss_code + " not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("ResourceFacingServiceSpec  with ID \"" + Rfss_code + "\" not found", e);
        }
    }

    @Override
    public boolean findByexternalNPCodeexist(String externalNPCode) {
        try {
            Optional<ResourceFacingServiceSpec> optionalResource =
                    resourceFacingServiceSpecRepository.findByexternalNPCode(externalNPCode);
            return optionalResource.isPresent();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding the resource");
        }
    }

    private void validateNotNullFields(ResourceFacingServiceSpec resourceFacingServiceSpec) {
        if (resourceFacingServiceSpec.getExternalNPCode() == null) {
            throw new InvalidInputException("Name cannot be null");
        }
    }
}
