package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InputException;
import com.Bcm.Exception.InvalidInput;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;


@Service
public class ProductOfferingServiceImpl implements ProductOfferingService {

    @Autowired
    ProductOfferingRepository productOfferingRepository;

    @Override
    public ProductOffering create(ProductOffering productOffering) {
        try {
            return productOfferingRepository.save(productOffering);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating product offering", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating product offering", e);
        }
    }

    @Override
    public List<ProductOffering> read() {
        try {
            return productOfferingRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading product offerings", e);
        }
    }

    @Override
    public ProductOffering update(int po_code, ProductOffering updatedProductOffering) {
        Optional<ProductOffering> existingProductOptional = productOfferingRepository.findById(po_code);

        if (existingProductOptional.isPresent()) {
            ProductOffering existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProductOffering.getName());
            existingProduct.setShdes(updatedProductOffering.getShdes());
            existingProduct.setParent(updatedProductOffering.getParent());
            existingProduct.setExternalLinkId(updatedProductOffering.getExternalLinkId());
            existingProduct.setProductSpecification(updatedProductOffering.getProductSpecification());
            existingProduct.setPoAttributes(updatedProductOffering.getPoAttributes());
            existingProduct.setProductRelation(updatedProductOffering.getProductRelation());
            existingProduct.setProductOfferRelation(updatedProductOffering.getProductOfferRelation());
            existingProduct.setLogicalResource(updatedProductOffering.getLogicalResource());
            existingProduct.setPhysicalResource(updatedProductOffering.getPhysicalResource());
            existingProduct.setBusinessProcess(updatedProductOffering.getBusinessProcess());
            existingProduct.setEligibility(updatedProductOffering.getEligibility());


            try {
                if (updatedProductOffering.getName() == null || updatedProductOffering.getDescription() == null) {
                    throw new InputException("Name and description cannot be null");
                }

                return productOfferingRepository.save(existingProduct);
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseOperationException("Error updating product offering with ID: " + po_code, e);
            } catch (ObjectOptimisticLockingFailureException e) {
                throw new ConcurrentModificationException("Another user has modified the product offering with ID: " + po_code + ". Please try again.");
            } catch (InputException e) {
                throw e;
            } catch (InvalidInput e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("An unexpected error occurred while updating product offering with ID: " + po_code, e);
            }
        } else {
            throw new ResourceNotFoundException("Could not find product offering with ID: " + po_code);
        }
    }

    @Override
    public String delete(int po_code) {
        if (!productOfferingRepository.existsById(po_code)) {
            throw new ResourceNotFoundException("Product offering with ID " + po_code + " not found");
        }

        try {
            productOfferingRepository.deleteById(po_code);
            return "Product offering with ID " + po_code + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting product offering with ID: " + po_code, e);
        }
    }

    @Override
    public ProductOffering findById(int po_code) {
        try {
            return productOfferingRepository.findById(po_code)
                    .orElseThrow(() -> new ResourceNotFoundException(" An unexpected error occurred while finding product offering with ID:" + po_code));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Product offering with ID \"" + po_code + "\" not found", e);
        }
    }

    @Override
    public List<ProductOffering> searchByKeyword(String name) {
        try {
            return productOfferingRepository.searchByKeyword(name);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while searching for product offerings by keyword: " + name, e);
        }
    }


    @Override
    public ProductOffering findByName(String name) {
        try {
            Optional<ProductOffering> optionalProductOffering = productOfferingRepository.findByname(name);
            return optionalProductOffering.orElseThrow(() -> new RuntimeException("Product Offering with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Product Offering");
        }
    }
}

