package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.MethodsAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Repository.ProductOfferingRepo.BusinessProcessRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BusinessProcessService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessProcessServiceImpl implements BusinessProcessService {

  final BusinessProcessRepository businessProcessRepository;
  final ProductOfferingRepository productOfferingRepository;

  @Override
  public BusinessProcess create(BusinessProcess businessProcess) {
    Optional<BusinessProcess> existingBusinessProcess = businessProcessRepository.findByName(businessProcess.getName());
    if (existingBusinessProcess.isPresent()) {
      throw new MethodsAlreadyExistsException(
          "Business Process with name: " + businessProcess.getName() + " already exists.");
    }
    try {
      return businessProcessRepository.save(businessProcess);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseOperationException("Error creating Business Process", e);
    }
  }

  @Override
  public List<BusinessProcess> read() {
    try {
      return businessProcessRepository.findAllOrderedByBusinessProcess();
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while retrieving Business");
    }
  }

  @Override
  public BusinessProcess update(int businessProcess_id, BusinessProcess updatedBusinessProcess) {
    Optional<BusinessProcess> existingBusinessOptional = businessProcessRepository.findById(businessProcess_id);
    if (existingBusinessOptional.isPresent()) {
      BusinessProcess existingBusinessProcess = existingBusinessOptional.get();

      String newName = updatedBusinessProcess.getName();
      if (!existingBusinessProcess.getName().equals(newName) && businessProcessRepository.existsByName(newName)) {
        throw new MethodsAlreadyExistsException("Business with name '" + newName + "' already exists.");
      }
      existingBusinessProcess.setName(updatedBusinessProcess.getName());
      existingBusinessProcess.setAction(updatedBusinessProcess.getAction());
      return businessProcessRepository.save(existingBusinessProcess);
    } else {
      throw new ResourceNotFoundException("BusinessProcess with ID " + businessProcess_id + " not found.");
    }
  }

  @Override
  public String delete(int businessProcess_id) {
    try {
      businessProcessRepository.deleteById(businessProcess_id);
      return ("businessProcess with ID " + businessProcess_id + " was successfully deleted");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for deleting Methods");
    }
  }

  @Override
  public BusinessProcess findById(int businessProcess_id) {
    try {
      Optional<BusinessProcess> optionalBusinessProcess = businessProcessRepository.findById(businessProcess_id);
      return optionalBusinessProcess.orElseThrow(
          () -> new RuntimeException("BusinessProcess with ID " + businessProcess_id + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding Methods");
    }
  }

  @Override
  public List<BusinessProcess> searchByKeyword(String name) {
    try {
      return businessProcessRepository.searchByKeyword(name);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for searching Methods by keyword");
    }
  }

  @Override
  public BusinessProcess findByName(String name) {
    try {
      Optional<BusinessProcess> optionalBusinessProcess = businessProcessRepository.findByName(name);
      return optionalBusinessProcess.orElseThrow(
          () -> new RuntimeException("BusinessProcess with name " + name + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding BusinessProcess");
    }
  }

  @Override
  public List<BusinessProcess> findBusinessProcessByProductId(int productId) {
    return businessProcessRepository.findBusinessProcessByProductId(productId);
  }
}
