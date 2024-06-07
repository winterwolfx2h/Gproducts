package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ProductPriceGroupAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductPriceGroup;
import com.Bcm.Repository.ProductOfferingRepo.ProductPriceGroupRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductPriceGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ProductPriceGroupServiceImpl implements ProductPriceGroupService {

    final ProductPriceGroupRepository productPriceGroupRepository;
    final ProductOfferingService productOfferingService;

    @Override
    public ProductPriceGroup create(ProductPriceGroup productPriceGroup) {
        try {
            if (findByNameexist(productPriceGroup.getName())) {
                throw new ProductPriceGroupAlreadyExistsException("ProductPriceGroup with the same name already exists");
            }
            return productPriceGroupRepository.save(productPriceGroup);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating ProductPriceGroup", e);
        }
    }


    @Override
    public List<ProductPriceGroup> read() {
        try {
            return productPriceGroupRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving product price groups ");
        }
    }

    @Override
    public ProductPriceGroup update(int productPriceGroupCode, ProductPriceGroup updatedProductPriceGroup) {
        Optional<ProductPriceGroup> existingProductPriceGroupOptional = productPriceGroupRepository.findById(productPriceGroupCode);
        if (existingProductPriceGroupOptional.isPresent()) {
            ProductPriceGroup existingProductPriceGroup = existingProductPriceGroupOptional.get();

            String newName = updatedProductPriceGroup.getName();
            // Check if there's another ProductPriceGroup with the same name
            if (!existingProductPriceGroup.getName().equals(newName) && productPriceGroupRepository.existsByName(newName)) {
                throw new ProductPriceGroupAlreadyExistsException("ProductPriceGroup with name '" + newName + "' already exists.");
            }

            existingProductPriceGroup.setName(newName);
            existingProductPriceGroup.setDescription(updatedProductPriceGroup.getDescription());
            return productPriceGroupRepository.save(existingProductPriceGroup);
        } else {
            throw new ResourceNotFoundException("ProductPriceGroup with ID " + productPriceGroupCode + " not found.");
        }
    }


    @Override
    public String delete(int productPriceGroupCode) {
        try {
            ProductPriceGroup ProductPriceGroup = findById(productPriceGroupCode);
            productPriceGroupRepository.deleteById(productPriceGroupCode);
            return ("ProductPriceGroup with ID " + productPriceGroupCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting ProductPriceGroup");
        }
    }

    @Override
    public ProductPriceGroup findById(int productPriceGroupCode) {
        try {
            Optional<ProductPriceGroup> optionalProductPriceGroup = productPriceGroupRepository.findById(productPriceGroupCode);
            return optionalProductPriceGroup.orElseThrow(() -> new RuntimeException("ProductPriceGroup with ID " + productPriceGroupCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductPriceGroup");
        }
    }

    @Override
    public List<ProductPriceGroup> searchByKeyword(String name) {
        try {
            return productPriceGroupRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching ProductPriceGroup by keyword");
        }
    }

    @Override
    public ProductPriceGroup findByName(String name) {
        try {
            Optional<ProductPriceGroup> optionalProductPriceGroup = productPriceGroupRepository.findByName(name);
            return optionalProductPriceGroup.orElseThrow(() -> new RuntimeException("ProductPriceGroup with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductPriceGroup");
        }
    }

    @Override
    public boolean findByNameexist(String name) {
        try {
            Optional<ProductPriceGroup> optionalProductPriceGroup = productPriceGroupRepository.findByName(name);
            return optionalProductPriceGroup.isPresent(); // Return true if ProductPriceGroup exists, false otherwise
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductPriceGroup");
        }
    }

    @Override
    public boolean existsById(int productPriceGroupCode) {
        return productPriceGroupRepository.existsById(productPriceGroupCode);
    }
}
