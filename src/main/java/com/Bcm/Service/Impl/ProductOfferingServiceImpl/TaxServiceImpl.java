package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.Tax;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Repository.ProductOfferingRepo.TaxRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.TaxService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaxServiceImpl implements TaxService {

  private static final String TID = "Tax with ID ";
  final TaxRepository taxRepository;
  final ProductOfferingRepository productOfferingRepository;
  final ProductRepository productRepository;

  @Override
  public Tax create(Tax tax) {
    LocalDate currentDate = LocalDate.now();

    if (tax.getValidFrom() == null
        || tax.getValidFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(currentDate)) {
      throw new RuntimeException("validFrom must be before the current date.");
    }

    if (tax.getValidTo() != null
        && tax.getValidTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(currentDate)) {
      throw new RuntimeException("validTo must be null or after the current date.");
    }

    if (tax.getValidTo() != null
        && tax.getValidTo()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .isBefore(tax.getValidFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
      throw new RuntimeException("validTo must be after validFrom.");
    }

    if (taxRepository.findTaxByName(tax.getName()).isPresent()) {
      throw new RuntimeException("Tax with name " + tax.getName() + " already exists");
    }

    return taxRepository.save(tax);
  }

  @Override
  public List<Tax> read() {
    try {
      return taxRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while retrieving Taxes");
    }
  }

  @Override
  public Tax update(int taxCode, Tax updatedTax) {
    Optional<Tax> existingTaxOptional = taxRepository.findById(taxCode);
    if (existingTaxOptional.isPresent()) {
      Tax existingTax = existingTaxOptional.get();

      LocalDate currentDate = LocalDate.now();

      if (updatedTax.getValidFrom() == null
          || updatedTax.getValidFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(currentDate)) {
        throw new RuntimeException("validFrom must be before the current date.");
      }

      if (updatedTax.getValidTo() != null
          && updatedTax.getValidTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(currentDate)) {
        throw new RuntimeException("validTo must be null or after the current date.");
      }

      if (updatedTax.getValidTo() != null
          && updatedTax
              .getValidTo()
              .toInstant()
              .atZone(ZoneId.systemDefault())
              .toLocalDate()
              .isBefore(updatedTax.getValidFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
        throw new RuntimeException("validTo must be after validFrom.");
      }

      existingTax.setName(updatedTax.getName());
      existingTax.setTaxType(updatedTax.getTaxType());
      existingTax.setValue(updatedTax.getValue());
      existingTax.setCustomerCategory(updatedTax.getCustomerCategory());
      existingTax.setExternalId(updatedTax.getExternalId());
      existingTax.setValidFrom(updatedTax.getValidFrom());
      existingTax.setValidTo(updatedTax.getValidTo());
      return taxRepository.save(existingTax);
    } else {
      throw new ResourceNotFoundException(TID + taxCode + " not found.");
    }
  }

  @Override
  public String delete(int taxCode) {
    try {
      taxRepository.deleteById(taxCode);
      return (TID + taxCode + " was successfully deleted");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for deleting Tax");
    }
  }

  @Override
  public Tax findById(int taxCode) {
    try {
      Optional<Tax> optionalTax = taxRepository.findById(taxCode);
      return optionalTax.orElseThrow(() -> new RuntimeException(TID + taxCode + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding Tax");
    }
  }

  @Override
  public boolean existsById(int taxCode) {
    return taxRepository.existsById(taxCode);
  }

  @Override
  public List<Tax> searchByName(String name) {
    try {
      return taxRepository.searchByName(name);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for searching Tax by keyword");
    }
  }

  @Override
  public List<Tax> findTaxesByProductId(int productId) {
    return taxRepository.findTaxesByProductId(productId);
  }
}
