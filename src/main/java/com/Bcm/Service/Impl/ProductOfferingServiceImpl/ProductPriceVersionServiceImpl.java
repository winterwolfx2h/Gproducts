package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ProductPriceVersionAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductPriceVersion;
import com.Bcm.Repository.ProductOfferingRepo.ProductPriceVersionRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductPriceVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ProductPriceVersionServiceImpl implements ProductPriceVersionService {

    final ProductPriceVersionRepository productPriceVersionRepository;
    final ProductOfferingService productOfferingService;

    @Override
    public ProductPriceVersion create(ProductPriceVersion productPriceVersion) {
        try {
            if (findByNameexist(productPriceVersion.getName())) {
                throw new ProductPriceVersionAlreadyExistsException("ProductPriceVersion with the same name already exists");
            }
            return productPriceVersionRepository.save(productPriceVersion);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating ProductPriceVersion", e);
        }
    }


    @Override
    public List<ProductPriceVersion> read() {
        try {
            return productPriceVersionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving product price groups ");
        }
    }

    @Override
    public ProductPriceVersion update(int productPriceVersionCode, ProductPriceVersion updatedProductPriceVersion) {
        Optional<ProductPriceVersion> existingProductPriceVersionOptional = productPriceVersionRepository.findById(productPriceVersionCode);
        if (existingProductPriceVersionOptional.isPresent()) {
            ProductPriceVersion existingProductPriceVersion = existingProductPriceVersionOptional.get();

            String newName = updatedProductPriceVersion.getName();
            // Check if there's another ProductPriceVersion with the same name
            if (!existingProductPriceVersion.getName().equals(newName) && productPriceVersionRepository.existsByName(newName)) {
                throw new ProductPriceVersionAlreadyExistsException("ProductPriceVersion with name '" + newName + "' already exists.");
            }

            existingProductPriceVersion.setName(newName);
            existingProductPriceVersion.setDescription(updatedProductPriceVersion.getDescription());
            return productPriceVersionRepository.save(existingProductPriceVersion);
        } else {
            throw new ResourceNotFoundException("ProductPriceVersion with ID " + productPriceVersionCode + " not found.");
        }
    }


    @Override
    public String delete(int productPriceVersionCode) {
        try {
            ProductPriceVersion ProductPriceVersion = findById(productPriceVersionCode);
            productPriceVersionRepository.deleteById(productPriceVersionCode);
            return ("ProductPriceVersion with ID " + productPriceVersionCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting ProductPriceVersion");
        }
    }

    @Override
    public ProductPriceVersion findById(int productPriceVersionCode) {
        try {
            Optional<ProductPriceVersion> optionalProductPriceVersion = productPriceVersionRepository.findById(productPriceVersionCode);
            return optionalProductPriceVersion.orElseThrow(() -> new RuntimeException("ProductPriceVersion with ID " + productPriceVersionCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductPriceVersion");
        }
    }

    @Override
    public List<ProductPriceVersion> searchByKeyword(String name) {
        try {
            return productPriceVersionRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching ProductPriceVersion by keyword");
        }
    }

    @Override
    public ProductPriceVersion findByName(String name) {
        try {
            Optional<ProductPriceVersion> optionalProductPriceVersion = productPriceVersionRepository.findByName(name);
            return optionalProductPriceVersion.orElseThrow(() -> new RuntimeException("ProductPriceVersion with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductPriceVersion");
        }
    }

    @Override
    public boolean findByNameexist(String name) {
        try {
            Optional<ProductPriceVersion> optionalProductPriceVersion = productPriceVersionRepository.findByName(name);
            return optionalProductPriceVersion.isPresent(); // Return true if ProductPriceVersion exists, false otherwise
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductPriceVersion");
        }
    }

    @Override
    public boolean existsById(int productPriceVersionCode) {
        return productPriceVersionRepository.existsById(productPriceVersionCode);
    }
}
