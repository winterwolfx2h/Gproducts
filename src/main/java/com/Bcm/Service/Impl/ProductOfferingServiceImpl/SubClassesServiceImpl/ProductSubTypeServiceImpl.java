package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductSubType;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ProductSubTypeRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ProductSubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSubTypeServiceImpl implements ProductSubTypeService {

    @Autowired
    ProductSubTypeRepository productSubTypeRepository;

    @Override
    public ProductSubType create(ProductSubType productSubType) {
        try {
            return productSubTypeRepository.save(productSubType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating ProductSubType");
        }
    }

    @Override
    public List<ProductSubType> read() {
        try {
            return productSubTypeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving ProductSubTypes");
        }
    }

    @Override
    public ProductSubType update(int po_ProdSubTypeCode, ProductSubType updatedProductSubType) {
        try {
            Optional<ProductSubType> existingProductSubTypeOptional = productSubTypeRepository.findById(po_ProdSubTypeCode);
            if (existingProductSubTypeOptional.isPresent()) {
                ProductSubType existingProductSubType = existingProductSubTypeOptional.get();
                existingProductSubType.setName(updatedProductSubType.getName());
                return productSubTypeRepository.save(existingProductSubType);
            } else {
                throw new RuntimeException("ProductSubType with ID " + po_ProdSubTypeCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating ProductSubType");
        }
    }

    @Override
    public String delete(int po_ProdSubTypeCode) {
        try {
            productSubTypeRepository.deleteById(po_ProdSubTypeCode);
            return ("ProductSubType with ID " + po_ProdSubTypeCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting ProductSubType");
        }
    }

    @Override
    public ProductSubType findById(int po_ProdSubTypeCode) {
        try {
            Optional<ProductSubType> optionalPlan = productSubTypeRepository.findById(po_ProdSubTypeCode);
            return optionalPlan.orElseThrow(() -> new RuntimeException("ProductSubType with ID " + po_ProdSubTypeCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductSubType");
        }
    }

    @Override
    public List<ProductSubType> searchByKeyword(String name) {
        try {
            return productSubTypeRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching ProductSubType by keyword");
        }
    }
}
