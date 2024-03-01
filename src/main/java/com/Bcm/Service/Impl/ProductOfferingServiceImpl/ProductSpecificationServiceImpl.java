package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import com.Bcm.Repository.ProductOfferingRepo.ProductSpecificationRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

    @Autowired
    ProductSpecificationRepository productSpecificationRepository;

    @Override
    public ProductSpecification create(ProductSpecification productSpecification) {
        return productSpecificationRepository.save(productSpecification);
    }

    @Override
    public List<ProductSpecification> read() {
        return productSpecificationRepository.findAll();
    }

    @Override
    public ProductSpecification update(int po_SpecCode, ProductSpecification updatedproductSpecification) {
        Optional<ProductSpecification> existingProductOptional = productSpecificationRepository.findById(po_SpecCode);

        if (existingProductOptional.isPresent()) {
            ProductSpecification existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedproductSpecification.getName());
            existingProduct.setCategory(updatedproductSpecification.getCategory());
            existingProduct.setPoPlan(updatedproductSpecification.getPoPlan());
            existingProduct.setExternalId(updatedproductSpecification.getExternalId());

            return productSpecificationRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find Product Specification with ID: " + po_SpecCode);
        }
    }

    @Override
    public String delete(int po_SpecCode) {
        productSpecificationRepository.deleteById(po_SpecCode);
        return ("Product Specification was successfully deleted");
    }

    @Override
    public ProductSpecification findById(int po_SpecCode) {
        Optional<ProductSpecification> optionalPlan = productSpecificationRepository.findById(po_SpecCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Product Specification with ID " + po_SpecCode + " not found"));
    }

    @Override
    public List<ProductSpecification> searchByKeyword(String name) {
        return productSpecificationRepository.searchByKeyword(name);
    }

    @Override
    public ProductSpecification findByName(String name) {
        try {
            Optional<ProductSpecification> optionalProductSpecification = productSpecificationRepository.findByname(name);
            return optionalProductSpecification.orElseThrow(() -> new RuntimeException("Product Specification with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Product Specification");
        }
    }

}



