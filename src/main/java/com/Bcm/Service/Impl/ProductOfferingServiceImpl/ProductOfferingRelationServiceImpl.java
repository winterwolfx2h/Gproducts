package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelation;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRelationRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferingRelationServiceImpl implements ProductOfferingRelationService {

    @Autowired
    ProductOfferingRelationRepository ProductOfferingRelationRepository;

    @Override
    public ProductOfferingRelation create(ProductOfferingRelation ProductOfferingRelation) {
        return ProductOfferingRelationRepository.save(ProductOfferingRelation);
    }

    @Override
    public List<ProductOfferingRelation> read() {
        return ProductOfferingRelationRepository.findAll();
    }

    @Override
    public ProductOfferingRelation update(int poRelation_Code, ProductOfferingRelation updatedProductOfferingRelation) {
        Optional<ProductOfferingRelation> existingProductOptional = ProductOfferingRelationRepository.findById(poRelation_Code);

        if (existingProductOptional.isPresent()) {
            ProductOfferingRelation existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProductOfferingRelation.getName());
            existingProduct.setType(updatedProductOfferingRelation.getType());
            existingProduct.setValidFor(updatedProductOfferingRelation.getValidFor());

            return ProductOfferingRelationRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find ProductOfferingRelation with ID: " + poRelation_Code);
        }
    }

    @Override
    public String delete(int poRelation_Code) {
        ProductOfferingRelationRepository.deleteById(poRelation_Code);
        return ("ProductOfferingRelation was successfully deleted");
    }

    @Override
    public ProductOfferingRelation findById(int poRelation_Code) {
        Optional<ProductOfferingRelation> optionalPlan = ProductOfferingRelationRepository.findById(poRelation_Code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("ProductOfferingRelation with ID " + poRelation_Code + " not found"));
    }

    @Override
    public List<ProductOfferingRelation> searchByKeyword(String name) {
        return ProductOfferingRelationRepository.searchByKeyword(name);
    }

    @Override
    public ProductOfferingRelation findByName(String name) {
        try {
            Optional<ProductOfferingRelation> optionalProductOfferingRelation = ProductOfferingRelationRepository.findByName(name);
            return optionalProductOfferingRelation.orElseThrow(() -> new RuntimeException("ProductOfferingRelation with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductOfferingRelation");
        }
    }


}

