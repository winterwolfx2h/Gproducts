package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Exception.*;
import com.Bcm.Model.ServiceABE.ResourceFacingServiceSpec;
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

    @Override
    public ResourceFacingServiceSpec create(ResourceFacingServiceSpec resourceFacingServiceSpec) {
        resourceFacingServiceSpec.setStatus("Working state");

        validateNotNullFields(resourceFacingServiceSpec);
        Optional<ResourceFacingServiceSpec> existingResource = resourceFacingServiceSpecRepository.findByName(resourceFacingServiceSpec.getName());

        if (existingResource.isPresent()) {
            throw new ResourceFacingServiceSpecException("Resource with the same name already exists");
        }

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
        Optional<ResourceFacingServiceSpec> existingResourceFacingServiceSpecOptional = resourceFacingServiceSpecRepository.findById(Rfss_code);

        if (existingResourceFacingServiceSpecOptional.isPresent()) {
            ResourceFacingServiceSpec existingResourceFacingServiceSpec = existingResourceFacingServiceSpecOptional.get();
            if (!existingResourceFacingServiceSpec.getName().equals(updatedResourceFacingServiceSpec.getName())) {
                if (resourceFacingServiceSpecRepository.findByName(updatedResourceFacingServiceSpec.getName()).isPresent()) {
                    throw new ServiceAlreadyExistsException("Resource with the same name already exists");
                }
            }
            existingResourceFacingServiceSpec.setName(updatedResourceFacingServiceSpec.getName());
            existingResourceFacingServiceSpec.setDescription(updatedResourceFacingServiceSpec.getDescription());
            existingResourceFacingServiceSpec.setValidFor(updatedResourceFacingServiceSpec.getValidFor());
            existingResourceFacingServiceSpec.setStatus(updatedResourceFacingServiceSpec.getStatus());

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
            throw new RuntimeException("An unexpected error occurred while deleting ResourceFacingServiceSpec with ID: " + Rfss_code, e);
        }
    }

    @Override
    public ResourceFacingServiceSpec findById(int Rfss_code) {
        try {
            return resourceFacingServiceSpecRepository.findById(Rfss_code)
                    .orElseThrow(() -> new ResourceNotFoundException("ResourceFacingServiceSpec  with ID " + Rfss_code + " not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("ResourceFacingServiceSpec  with ID \"" + Rfss_code + "\" not found", e);
        }
    }

    @Override
    public ResourceFacingServiceSpec changeServiceStatus(int Rfss_code) {
        try {
            ResourceFacingServiceSpec existingResource = findById(Rfss_code);

            switch (existingResource.getStatus()) {
                case "Working state":
                    existingResource.setStatus("Validated");
                    break;

                case "Validated":
                    existingResource.setStatus("Suspended");
                    break;

                case "Suspended":
                    throw new ServiceLogicException("ResourceFacingServiceSpec " + existingResource.getName() + " isn't fit to be offered for sale anymore.");

                default:
                    throw new InvalidInputException("Invalid status transition.");
            }

            return resourceFacingServiceSpecRepository.save(existingResource);

        } catch (ServiceLogicException e) {
            throw e;
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("ResourceFacingServiceSpec with ID \"" + Rfss_code + "\" not found", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while changing ResourceFacingServiceSpec status", e);
        }
    }


    @Override
    public boolean findByNameexist(String name) {
        try {
            Optional<ResourceFacingServiceSpec> optionalResource = resourceFacingServiceSpecRepository.findByName(name);
            return optionalResource.isPresent();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding the resource");
        }
    }

    private void validateNotNullFields(ResourceFacingServiceSpec resourceFacingServiceSpec) {
        if (resourceFacingServiceSpec.getName() == null) {
            throw new InvalidInputException("Name cannot be null");
        }
    }
}
