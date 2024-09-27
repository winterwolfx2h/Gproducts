package com.Bcm.Service.Impl.Product;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.Product.ProductCharacteristics;
import com.Bcm.Repository.Product.PCharacteristicsRepository;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Service.Srvc.ProductSrvc.PCharacteristicsService;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PCharacteristicsServiceImp implements PCharacteristicsService {
  final PCharacteristicsRepository pCharacteristicsRepository;
  private final ProductRepository productRepository;

  @Transactional
  public ProductCharacteristics create(ProductCharacteristics productCharacteristics) {
    if (productCharacteristics == null) {
      throw new InvalidInputException("Product Characteristic is missing");
    }

    try {
      Product product =
          productRepository
              .findById(productCharacteristics.getProductId())
              .orElseThrow(() -> new EntityNotFoundException("Product haven't been found"));

      productRepository.save(product);

      return pCharacteristicsRepository.save(productCharacteristics);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseOperationException("Error creating product  Characteristic", e);
    } catch (Exception e) {
      throw new RuntimeException("An unexpected error occurred while creating product  Characteristic", e);
    }
  }

  @Override
  public List<ProductCharacteristics> read() {
    return pCharacteristicsRepository.findAll();
  }

  @Override
  public ProductCharacteristics update(int pCharacteristic_code, ProductCharacteristics updatedProductCharacteristics) {
    Optional<ProductCharacteristics> existingProductCharacteristicsOptional =
        pCharacteristicsRepository.findById(pCharacteristic_code);

    if (existingProductCharacteristicsOptional.isPresent()) {
      ProductCharacteristics existingProductCharacteristics = existingProductCharacteristicsOptional.get();

      existingProductCharacteristics.setName(updatedProductCharacteristics.getName());

      if (updatedProductCharacteristics.getValueDescription() != null) {
        existingProductCharacteristics.getValueDescription().clear();
        existingProductCharacteristics
            .getValueDescription()
            .addAll(updatedProductCharacteristics.getValueDescription());
      }

      return pCharacteristicsRepository.save(existingProductCharacteristics);
    } else {
      throw new RuntimeException("ProductCharacteristics with ID: " + pCharacteristic_code + " was not found");
    }
  }

  @Override
  public String delete(int pCharacteristic_code) {
    pCharacteristicsRepository.deleteById(pCharacteristic_code);
    return ("ProductCharacteristics was successfully deleted");
  }

  @Override
  public ProductCharacteristics findById(int pCharacteristic_code) {
    Optional<ProductCharacteristics> optionalPlan = pCharacteristicsRepository.findById(pCharacteristic_code);
    return optionalPlan.orElseThrow(
        () -> new RuntimeException("ProductCharacteristics with ID " + pCharacteristic_code + " Wasn't found"));
  }

  @Override
  public ProductCharacteristics findByDescription(String description) {
    try {
      Optional<ProductCharacteristics> optionalProductCharacteristics =
          pCharacteristicsRepository.findByValueDescription_Value(description);
      return optionalProductCharacteristics.orElseThrow(
          () -> new RuntimeException("ProductCharacteristics with Description " + description + " couldn't be found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding ProductCharacteristics");
    }
  }

  @Override
  public boolean existsById(int pCharacteristic_code) {
    return pCharacteristicsRepository.existsById(pCharacteristic_code);
  }

  @Override
  public List<ProductCharacteristics> findByProductId(int productId) {
    return pCharacteristicsRepository.findByProductId(productId);
  }
}
