package com.Bcm.Service.Impl.ProductResourceServiceImpl;

import com.Bcm.Exception.*;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.Bcm.Repository.ProductResourceRepository.PhysicalResourceRepository;
import com.Bcm.Service.Srvc.ProductResourceSrvc.PhysicalResourceService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhysicalResourceServiceImpl implements PhysicalResourceService {

  final PhysicalResourceRepository physicalResourceRepository;

  @Override
  public PhysicalResource create(PhysicalResource physicalResource) {

    physicalResource.setStatus("Working state");

    validateNotNullFields(physicalResource);
    List<PhysicalResource> existingResource = physicalResourceRepository.findByName(physicalResource.getName());

    if (!existingResource.isEmpty()) {
      throw new ServiceAlreadyExistsException(
          "Physical resource with name '" + physicalResource.getName() + "' already exists.");
    }

    try {
      return physicalResourceRepository.save(physicalResource);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseOperationException("Error creating Physical resource", e);
    } catch (Exception e) {
      throw new RuntimeException("An unexpected error occurred while creating Physical resource", e);
    }
  }

  @Override
  public List<PhysicalResource> read() {
    return physicalResourceRepository.findAll();
  }

  @Override
  public PhysicalResource update(int PR_id, PhysicalResource updatedPhysicalResource) {
    Optional<PhysicalResource> existingProductOptional = physicalResourceRepository.findById(PR_id);

    if (existingProductOptional.isPresent()) {
      PhysicalResource existingProduct = existingProductOptional.get();
      existingProduct.setName(updatedPhysicalResource.getName());
      existingProduct.setPhysicalResourceType(updatedPhysicalResource.getPhysicalResourceType());
      existingProduct.setPhysicalResourceFormat(updatedPhysicalResource.getPhysicalResourceFormat());
      existingProduct.setGenerationType(updatedPhysicalResource.getGenerationType());
      return physicalResourceRepository.save(existingProduct);
    } else {
      throw new RuntimeException("Could not find PhysicalResource with ID: " + PR_id);
    }
  }

  @Override
  public String delete(int PR_id) {
    physicalResourceRepository.deleteById(PR_id);
    return ("PhysicalResource was successfully deleted");
  }

  @Override
  public PhysicalResource findById(int PR_id) {
    Optional<PhysicalResource> optionalPlan = physicalResourceRepository.findById(PR_id);
    return optionalPlan.orElseThrow(() -> new RuntimeException("PhysicalResource with ID " + PR_id + " not found"));
  }

  @Override
  public List<PhysicalResource> searchByKeyword(String physicalResourceType) {
    return physicalResourceRepository.searchByKeyword(physicalResourceType);
  }

  @Override
  public PhysicalResource findByPhysicalResourceType(String physicalResourceType) {
    try {
      Optional<PhysicalResource> optionalPhysicalResource =
          physicalResourceRepository.findByPhysicalResourceType(physicalResourceType);
      return optionalPhysicalResource.orElseThrow(
          () -> new RuntimeException("PhysicalResource with type " + physicalResourceType + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding PhysicalResource");
    }
  }

  @Override
  public boolean existsById(int PR_id) {
    return physicalResourceRepository.existsById(PR_id);
  }

  private void validateNotNullFields(PhysicalResource physicalResource) {
    if (physicalResource.getPhysicalResourceType() == null) {
      throw new InvalidInputException("PhysicalResourceType cannot be null");
    }
  }

  @Override
  public PhysicalResource changeServiceStatus(int PR_id) {
    try {
      PhysicalResource existingResource = findById(PR_id);

      switch (existingResource.getStatus()) {
        case "Working state":
          existingResource.setStatus("Validated");
          break;

        case "Validated":
          existingResource.setStatus("Suspended");
          break;

        case "Suspended":
          throw new ServiceLogicException(
              "PhysicalResource "
                  + existingResource.getPhysicalResourceType()
                  + " isn't fit to be offered for sale anymore.");

        default:
          throw new InvalidInputException("Invalid status transition.");
      }

      return physicalResourceRepository.save(existingResource);

    } catch (ServiceLogicException e) {
      throw e;
    } catch (ResourceNotFoundException e) {
      throw new RuntimeException("PhysicalResource with ID \"" + PR_id + "\" not found", e);
    } catch (Exception e) {
      throw new RuntimeException("An unexpected error occurred while changing PhysicalResource status", e);
    }
  }
}
