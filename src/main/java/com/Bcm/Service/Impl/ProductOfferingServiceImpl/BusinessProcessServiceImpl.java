package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.MethodsAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Model.ProductOfferingABE.Methods;
import com.Bcm.Repository.ProductOfferingRepo.BusinessProcessRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Repository.ProductOfferingRepo.TypeRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BusinessProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessProcessServiceImpl implements BusinessProcessService {

  final BusinessProcessRepository businessProcessRepository;

  final ProductOfferingRepository productOfferingRepository;

  @Override
  public BusinessProcess create(BusinessProcess businessProcess) {
    Optional<BusinessProcess> existingBusnissProcess = businessProcessRepository.findByName(businessProcess.getName());
    if (existingBusnissProcess.isPresent()) {
      throw new MethodsAlreadyExistsException("busniss with name: " + businessProcess.getName() + " already exists.");
    }
    try {
      return businessProcessRepository.save(businessProcess);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseOperationException("Error creating busniss", e);
    }
  }

  @Override
  public List<BusinessProcess> read() {
    try {
      return businessProcessRepository.findAllOrderedByBusnissProcess();
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while retrieving busniss");
    }
  }

  @Override
  public BusinessProcess update(int businessProcess_id, BusinessProcess updatedBusnissProcess) {
    Optional<BusinessProcess> existingBusnissOptional = businessProcessRepository.findById(businessProcess_id);
    if (existingBusnissOptional.isPresent()) {
      BusinessProcess existingBusnissProcess = existingBusnissOptional.get();

      String newName = updatedBusnissProcess.getName();
      // Check if there's another Methods with the same name
      if (!existingBusnissProcess.getName().equals(newName) && businessProcessRepository.existsByName(newName)) {
        throw new MethodsAlreadyExistsException("busniss with name '" + newName + "' already exists.");
      }
      existingBusnissProcess.setName(updatedBusnissProcess.getName());
      existingBusnissProcess.setDescription(updatedBusnissProcess.getDescription());
      return businessProcessRepository.save(existingBusnissProcess);
    } else {
      throw new ResourceNotFoundException("BusinessProcess with ID " + businessProcess_id + " not found.");
    }
  }

  @Override
  public String delete(int businessProcess_id) {
    try {
      BusinessProcess businessProcess = findById(businessProcess_id);
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
      return optionalBusinessProcess.orElseThrow(() -> new RuntimeException("BusinessProcess with ID " + businessProcess_id + " not found"));
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
      return optionalBusinessProcess.orElseThrow(() -> new RuntimeException("BusinessProcess with name " + name + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding Methods");
    }
  }


}
