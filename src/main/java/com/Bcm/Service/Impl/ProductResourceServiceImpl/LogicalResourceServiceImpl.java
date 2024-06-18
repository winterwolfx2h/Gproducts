package com.Bcm.Service.Impl.ProductResourceServiceImpl;

import com.Bcm.Exception.DuplicateResourceException;
import com.Bcm.Model.ProductResourceABE.LogicalResource;
import com.Bcm.Repository.ProductResourceRepository.LogicalResourceRepository;
import com.Bcm.Service.Srvc.ProductResourceSrvc.LogicalResourceService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogicalResourceServiceImpl implements LogicalResourceService {

  final LogicalResourceRepository logicalResourceRepository;

  @Override
  public LogicalResource create(LogicalResource logicalResource) {
    if (logicalResourceRepository.existsByName(logicalResource.getName())) {
      throw new DuplicateResourceException(
          "LogicalResource with name " + logicalResource.getName() + " already exists");
    }
    logicalResource.setStatus("Working state");
    return logicalResourceRepository.save(logicalResource);
  }

  @Override
  public List<LogicalResource> read() {
    return logicalResourceRepository.findAll();
  }

  @Override
  public LogicalResource update(int LR_id, LogicalResource updatedLogicalResource) {
    Optional<LogicalResource> existingProductOptional = logicalResourceRepository.findById(LR_id);

    if (existingProductOptional.isPresent()) {
      LogicalResource existingProduct = existingProductOptional.get();
      existingProduct.setName(updatedLogicalResource.getName());
      existingProduct.setLogicalResourceType(updatedLogicalResource.getLogicalResourceType());
      existingProduct.setLogicalResourceFormat(updatedLogicalResource.getLogicalResourceFormat());
      existingProduct.setLength(updatedLogicalResource.getLength());
      existingProduct.setGenerationType(updatedLogicalResource.getGenerationType());
      existingProduct.setStatus(updatedLogicalResource.getStatus());

      return logicalResourceRepository.save(existingProduct);
    } else {
      throw new RuntimeException("Could not find LogicalResource with ID: " + LR_id);
    }
  }

  @Override
  public String delete(int LR_id) {
    logicalResourceRepository.deleteById(LR_id);
    return ("LogicalResource was successfully deleted");
  }

  @Override
  public LogicalResource findById(int LR_id) {
    Optional<LogicalResource> optionalPlan = logicalResourceRepository.findById(LR_id);
    return optionalPlan.orElseThrow(() -> new RuntimeException("LogicalResource with ID " + LR_id + " not found"));
  }

  @Override
  public List<LogicalResource> searchByKeyword(String logicalResourceType) {
    return logicalResourceRepository.searchByKeyword(logicalResourceType);
  }

  @Override
  public LogicalResource findByLogicalResourceType(String logicalResourceType) {
    try {
      Optional<LogicalResource> optionalLogicalResource =
          logicalResourceRepository.findByLogicalResourceType(logicalResourceType);
      return optionalLogicalResource.orElseThrow(
          () -> new RuntimeException("LogicalResource with type " + logicalResourceType + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding LogicalResource");
    }
  }

  @Override
  public boolean existsById(int LR_id) {
    return logicalResourceRepository.existsById(LR_id);
  }

  @Override
  public boolean findByNameExist(String name) {
    try {
      Optional<LogicalResource> optionalLogicalResource = logicalResourceRepository.findByName(name);
      return optionalLogicalResource.isPresent();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding LogicalResource");
    }
  }

  @Override
  public LogicalResource findByName(String name) {
    try {
      Optional<LogicalResource> optionalLogicalResource = logicalResourceRepository.findByName(name);
      return optionalLogicalResource.orElseThrow(
          () -> new RuntimeException("LogicalResource with ID " + name + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding LogicalResource");
    }
  }
}
