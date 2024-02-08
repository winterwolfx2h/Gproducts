package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductTechnicalType;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ProductTechnicalTypeRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ProductTechnicalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTechnicalTypeServiceImpl implements ProductTechnicalTypeService {

    @Autowired
    ProductTechnicalTypeRepository productTechnicalTypeRepository;

    @Override
    public ProductTechnicalType create(ProductTechnicalType productTechnicalType) {
        try {
            return productTechnicalTypeRepository.save(productTechnicalType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating Product Technical Type");
        }
    }

    @Override
    public List<ProductTechnicalType> read() {
        try {
            return productTechnicalTypeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Product Technical Types");
        }
    }

    @Override
    public ProductTechnicalType update(int po_ProdTechTypeCode, ProductTechnicalType updatedProductTechnicalType) {
        try {
            Optional<ProductTechnicalType> existingProductTechnicalTypeOptional = productTechnicalTypeRepository.findById(po_ProdTechTypeCode);
            if (existingProductTechnicalTypeOptional.isPresent()) {
                ProductTechnicalType existingProductTechnicalType = existingProductTechnicalTypeOptional.get();
                existingProductTechnicalType.setName(updatedProductTechnicalType.getName());
                return productTechnicalTypeRepository.save(existingProductTechnicalType);
            } else {
                throw new RuntimeException("Product Technical Type with ID " + po_ProdTechTypeCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating Product Technical Type");
        }
    }

    @Override
    public String delete(int po_ProdTechTypeCode) {
        try {
            productTechnicalTypeRepository.deleteById(po_ProdTechTypeCode);
            return ("Product Technical Type with ID " + po_ProdTechTypeCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Product Technical Type");
        }
    }

    @Override
    public ProductTechnicalType findById(int po_ProdTechTypeCode) {
        try {
            Optional<ProductTechnicalType> optionalPlan = productTechnicalTypeRepository.findById(po_ProdTechTypeCode);
            return optionalPlan.orElseThrow(() -> new RuntimeException("Product Technical Type with ID " + po_ProdTechTypeCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Product Technical Type");
        }
    }

    @Override
    public List<ProductTechnicalType> searchByKeyword(String name) {
        try {
            return productTechnicalTypeRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching Product Technical Type by keyword");
        }
    }
}
