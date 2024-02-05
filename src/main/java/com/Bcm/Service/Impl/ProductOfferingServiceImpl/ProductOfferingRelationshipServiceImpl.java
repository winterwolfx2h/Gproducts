package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelationship;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRelationshipRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferingRelationshipServiceImpl implements ProductOfferingRelationshipService {

    @Autowired
    ProductOfferingRelationshipRepository productOfferingRelationshipRepository;

    @Autowired
    ProductOfferingRelationshipService productOfferingRelationshipService;

    @Override
    public ProductOfferingRelationship create(ProductOfferingRelationship productOfferingRelationship) {
        return productOfferingRelationshipRepository.save(productOfferingRelationship);
    }

    @Override
    public List<ProductOfferingRelationship> read() {
        return productOfferingRelationshipRepository.findAll();
    }

    @Override
    public ProductOfferingRelationship update(int por_Code, ProductOfferingRelationship updatedProductOfferingRelationship) {
        Optional<ProductOfferingRelationship> existingProductOptional = productOfferingRelationshipRepository.findById(por_Code);

        if (existingProductOptional.isPresent()) {
            ProductOfferingRelationship existingProduct = existingProductOptional.get();
            existingProduct.setType(updatedProductOfferingRelationship.getType());
            existingProduct.setValidFor(updatedProductOfferingRelationship.getValidFor());

            return productOfferingRelationshipRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find product offering with ID: " + por_Code);
        }
    }
    @Override
    public String delete(int por_Code) {
        productOfferingRelationshipRepository.deleteById(por_Code);
        return ("plan was successfully deleted");
    }
    @Override
    public ProductOfferingRelationship findById(int por_Code) {
        Optional<ProductOfferingRelationship> optionalPlan = productOfferingRelationshipRepository.findById(por_Code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Plan with ID " + por_Code + " not found"));
    }

}