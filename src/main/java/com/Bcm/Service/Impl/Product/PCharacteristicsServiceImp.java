package com.Bcm.Service.Impl.Product;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.Product.ProductCharacteristics;
import com.Bcm.Repository.Product.PCharacteristicsRepository;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Service.Srvc.ProductSrvc.PCharacteristicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
            // Fetch the product by its Product_id
            Product product =
                    productRepository
                            .findById(productCharacteristics.getProduct_id())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            productRepository.save(product);

            // Save the ProductCharacteristics
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
            throw new RuntimeException("ProductCharacteristics with ID: " + pCharacteristic_code + " not found");
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
                () -> new RuntimeException("ProductCharacteristics with ID " + pCharacteristic_code + " not found"));
    }

    @Override
    public ProductCharacteristics findByDescription(String description) {
        try {
            Optional<ProductCharacteristics> optionalProductCharacteristics =
                    pCharacteristicsRepository.findByValueDescription_Value(description);
            return optionalProductCharacteristics.orElseThrow(
                    () -> new RuntimeException("ProductCharacteristics with Description " + description + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductCharacteristics");
        }
    }

    public ProductCharacteristics saveOrUpdate(int pCharacteristic_code, ProductCharacteristics productCharacteristics)
            throws InvalidInputException {
        Optional<ProductCharacteristics> existingCharacteristic = pCharacteristicsRepository.findById(pCharacteristic_code);

        if (existingCharacteristic.isPresent()) {
            ProductCharacteristics existingProductCharacteristics = existingCharacteristic.get();
            existingProductCharacteristics.setName(productCharacteristics.getName());
            // Set other fields as necessary
            return pCharacteristicsRepository.save(existingProductCharacteristics);
        } else {
            // Add new Characteristic
            productCharacteristics.setPCharacteristic_code(pCharacteristic_code); // Ensure the ID is set correctly
            return pCharacteristicsRepository.save(productCharacteristics);
        }
    }

    @Override
    public boolean existsById(int pCharacteristic_code) {
        return pCharacteristicsRepository.existsById(pCharacteristic_code);
    }
}
