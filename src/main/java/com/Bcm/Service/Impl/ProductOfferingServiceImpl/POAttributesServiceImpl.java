package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Repository.ProductOfferingRepo.POAttributesRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.POAttributesService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class POAttributesServiceImpl implements POAttributesService {

  final POAttributesRepository poAttributesRepository;

  @Override
  public POAttributes create(POAttributes poAttributes) {
    try {
      return poAttributesRepository.save(poAttributes);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseOperationException("Error creating product offering Attribute", e);
    } catch (Exception e) {
      throw new RuntimeException("An unexpected error occurred while creating product offering Attribute", e);
    }
  }

  @Override
  public List<POAttributes> read() {
    return poAttributesRepository.findAll();
  }

  @Override
  public POAttributes update(int poAttribute_code, POAttributes updatedPOAttributes) {
    Optional<POAttributes> existingPOAttributesOptional = poAttributesRepository.findById(poAttribute_code);

    if (existingPOAttributesOptional.isPresent()) {
      POAttributes existingPOAttributes = existingPOAttributesOptional.get();

      existingPOAttributes.setName(updatedPOAttributes.getName());
      existingPOAttributes.setCategory(updatedPOAttributes.getCategory());
      existingPOAttributes.setBsexternalId(updatedPOAttributes.getBsexternalId());
      existingPOAttributes.setCsexternalId(updatedPOAttributes.getCsexternalId());
      existingPOAttributes.setCharType(updatedPOAttributes.getCharType());
      existingPOAttributes.setCharValue(updatedPOAttributes.getCharValue());

      if (updatedPOAttributes.getValueDescription() != null) {
        existingPOAttributes.getValueDescription().clear();
        existingPOAttributes.getValueDescription().addAll(updatedPOAttributes.getValueDescription());
      }

      return poAttributesRepository.save(existingPOAttributes);
    } else {
      throw new RuntimeException("POAttributes with ID: " + poAttribute_code + " not found");
    }
  }

  @Override
  public String delete(int poAttribute_code) {
    poAttributesRepository.deleteById(poAttribute_code);
    return ("POAttributes was successfully deleted");
  }

  @Override
  public POAttributes findById(int poAttribute_code) {
    Optional<POAttributes> optionalPlan = poAttributesRepository.findById(poAttribute_code);
    return optionalPlan.orElseThrow(
        () -> new RuntimeException("POAttributes with ID " + poAttribute_code + " not found"));
  }

  @Override
  public POAttributes findByDescription(String description) {
    try {
      Optional<POAttributes> optionalPOAttributes = poAttributesRepository.findByValueDescription_Value(description);
      return optionalPOAttributes.orElseThrow(
          () -> new RuntimeException("POAttributes with Description " + description + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding POAttributes");
    }
  }

  @Override
  public boolean existsById(int poAttribute_code) {
    return poAttributesRepository.existsById(poAttribute_code);
  }
}
