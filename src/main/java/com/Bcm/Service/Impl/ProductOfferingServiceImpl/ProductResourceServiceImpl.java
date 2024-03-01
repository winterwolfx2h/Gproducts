package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductResource;
import com.Bcm.Repository.ProductOfferingRepo.ProductResourceRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductResourceServiceImpl implements ProductResourceService {

    @Autowired
    ProductResourceRepository productResourceRepository;

    @Override
    public ProductResource create(ProductResource ProductResource) {
        return productResourceRepository.save(ProductResource);
    }

    @Override
    public List<ProductResource> read() {
        return productResourceRepository.findAll();
    }

    @Override
    public ProductResource update(int PrResId, ProductResource updatedProductResource) {
        Optional<ProductResource> existingProductOptional = productResourceRepository.findById(PrResId);

        if (existingProductOptional.isPresent()) {
            ProductResource existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProductResource.getName());
            existingProduct.setLogicalResourceType(updatedProductResource.getLogicalResourceType());
            existingProduct.setLogicalResourceFromat(updatedProductResource.getLogicalResourceFromat());
            existingProduct.setLrServiceId(updatedProductResource.getLrServiceId());
            existingProduct.setPhysicalResourceType(updatedProductResource.getPhysicalResourceType());
            existingProduct.setPhysicalResourceFromat(updatedProductResource.getPhysicalResourceFromat());
            existingProduct.setPrServiceId(updatedProductResource.getPrServiceId());

            return productResourceRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find Product Resource with ID: " + PrResId);
        }
    }

    @Override
    public String delete(int PrResId) {
        productResourceRepository.deleteById(PrResId);
        return ("Product Resource was successfully deleted");
    }

    @Override
    public ProductResource findById(int PrResId) {
        Optional<ProductResource> optionalPlan = productResourceRepository.findById(PrResId);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Product Resource with ID " + PrResId + " not found"));
    }

    @Override
    public List<ProductResource> searchByKeyword(String name) {
        return productResourceRepository.searchByKeyword(name);
    }

    @Override
    public ProductResource findByName(String name) {
        try {
            Optional<ProductResource> optionalProductResource = productResourceRepository.findByname(name);
            return optionalProductResource.orElseThrow(() -> new RuntimeException("Product Resource with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Product Resource");
        }
    }
}

