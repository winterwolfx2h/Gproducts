package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.Tax;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Repository.ProductOfferingRepo.TaxRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.TaxService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TaxServiceImpl implements TaxService {

  final TaxRepository TaxRepository;
  final ProductOfferingRepository productOfferingRepository;
  final ProductRepository productRepository;

  @Override
  public Tax create(Tax tax) {
    try {
      Product product =
          productRepository
              .findById(tax.getProduct_id())
              .orElseThrow(() -> new EntityNotFoundException("Product not found"));

      productRepository.save(product);
      return TaxRepository.save(tax);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseOperationException("Error creating Tax", e);
    }
  }

  @Override
  @Transactional
  public List<Tax> create(List<Tax> taxes) {
    List<Tax> createdTaxes = new ArrayList<>();

    for (Tax Tax : taxes) {
      if (!TaxRepository.existsById(Tax.getTaxCode())) {
        createdTaxes.add(TaxRepository.save(Tax));

        int productId = Tax.getProduct_id();
        ProductOffering productOffering =
            productOfferingRepository
                .findById(productId)
                .orElseThrow(
                    () -> new EntityNotFoundException("ProductOffering not found for Product_id: " + productId));

        productOffering.setWorkingStep("Product Price");
        productOfferingRepository.save(productOffering);
      }
    }

    return createdTaxes;
  }

  @Override
  public List<Tax> read() {
    try {
      return TaxRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while retrieving Taxes");
    }
  }

  @Override
  public Tax update(int taxCode, Tax updatedTax) {
    Optional<Tax> existingTaxOptional = TaxRepository.findById(taxCode);
    if (existingTaxOptional.isPresent()) {
      Tax existingTax = existingTaxOptional.get();
      existingTax.setName(updatedTax.getName());
      existingTax.setTaxType(updatedTax.getTaxType());
      existingTax.setValue(updatedTax.getValue());
      existingTax.setCustomerCategory(updatedTax.getCustomerCategory());
      return TaxRepository.save(existingTax);
    } else {
      throw new ResourceNotFoundException("Tax with ID " + taxCode + " not found.");
    }
  }

  @Override
  public String delete(int taxCode) {
    try {
      Tax tax = findById(taxCode);
      TaxRepository.deleteById(taxCode);
      return ("Tax with ID " + taxCode + " was successfully deleted");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for deleting Tax");
    }
  }

  @Override
  public Tax findById(int taxCode) {
    try {
      Optional<Tax> optionalTax = TaxRepository.findById(taxCode);
      return optionalTax.orElseThrow(() -> new RuntimeException("Tax with ID " + taxCode + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding Tax");
    }
  }

  @Override
  public boolean existsById(int taxCode) {
    return TaxRepository.existsById(taxCode);
  }

  @Override
  public List<Tax> searchByName(String name) {
    try {
      return TaxRepository.searchByName(name);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for searching Tax by keyword");
    }
  }
}
