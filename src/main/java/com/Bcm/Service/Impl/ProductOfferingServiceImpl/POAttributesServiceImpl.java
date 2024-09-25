package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.ProductOfferingRepo.POAttributesRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.POAttributesService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Primary
public class POAttributesServiceImpl implements POAttributesService {

  final POAttributesRepository poAttributesRepository;
  final CustomerFacingServiceSpecService customerFacingServiceSpecService;

  private final ProductOfferingRepository productOfferingRepository;
  private static final String PID = "POAttributes with ID: ";
  private static final String NTF = " not found";

  @Transactional
  public POAttributes create(POAttributes poAttributes) {
    try {
      ProductOffering productOffering =
          productOfferingRepository
              .findById(poAttributes.getProductId())
              .orElseThrow(() -> new EntityNotFoundException("Product not found"));

      productOffering.setWorkingStep("PO-Attribute");
      productOfferingRepository.save(productOffering);

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
      existingPOAttributes.setAttributeType(updatedPOAttributes.getAttributeType());
      existingPOAttributes.setDataType(updatedPOAttributes.getDataType());

      if (updatedPOAttributes.getValueDescription() != null) {
        existingPOAttributes.getValueDescription().clear();
        existingPOAttributes.getValueDescription().addAll(updatedPOAttributes.getValueDescription());
      }

      return poAttributesRepository.save(existingPOAttributes);
    } else {
      throw new RuntimeException(PID + poAttribute_code + NTF);
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
    return optionalPlan.orElseThrow(() -> new RuntimeException(PID + poAttribute_code + NTF));
  }

  @Override
  public POAttributes findByDescription(String description) {
    try {
      Optional<POAttributes> optionalPOAttributes = poAttributesRepository.findByValueDescription_Value(description);
      return optionalPOAttributes.orElseThrow(
          () -> new RuntimeException("POAttributes with Description " + description + NTF));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding POAttributes");
    }
  }

  public POAttributes saveOrUpdate(int poAttribute_code, POAttributes poAttributes) throws InvalidInputException {
    Optional<POAttributes> existingAttribute = poAttributesRepository.findById(poAttribute_code);

    if (existingAttribute.isPresent()) {
      POAttributes existingPOAttributes = existingAttribute.get();
      existingPOAttributes.setName(poAttributes.getName());
      existingPOAttributes.setCategory(poAttributes.getCategory());
      existingPOAttributes.setBsexternalId(poAttributes.getBsexternalId());
      existingPOAttributes.setCsexternalId(poAttributes.getCsexternalId());
      existingPOAttributes.setAttributeType(poAttributes.getAttributeType());
      existingPOAttributes.setDataType(poAttributes.getDataType());
      existingPOAttributes.setValueDescription(poAttributes.getValueDescription());
      existingPOAttributes.setDefaultMaxSize(poAttributes.getDefaultMaxSize());
      existingPOAttributes.setMandatory(poAttributes.getMandatory());

      return poAttributesRepository.save(existingPOAttributes);
    } else {
      poAttributes.setPoAttribute_code(poAttribute_code);
      return poAttributesRepository.save(poAttributes);
    }
  }

  @Override
  public boolean existsById(int poAttribute_code) {
    return poAttributesRepository.existsById(poAttribute_code);
  }

  @Override
  public List<POAttributes> readByProductId(int productId) {
    return poAttributesRepository.findByProductId(productId);
  }

  public boolean validateDependentCfs(String dependentCfs) {
    return dependentCfs != null
        && !dependentCfs.isEmpty()
        && customerFacingServiceSpecService.findByNameexist(dependentCfs);
  }

  public void setDefaultValueDescriptions(POAttributes poAttribute) {
    if (poAttribute.getValueDescription() == null) {
      poAttribute.setValueDescription(new ArrayList<>());
    }

    for (POAttributes.ValueDescription valueDescription : poAttribute.getValueDescription()) {
      if (valueDescription.getDescription() == null) {
        valueDescription.setDescription("Default Description");
      }
    }
  }

  public boolean isCategoryValid(String attributeCategoryName) {
    return attributeCategoryName != null && !attributeCategoryName.isEmpty();
  }
}
