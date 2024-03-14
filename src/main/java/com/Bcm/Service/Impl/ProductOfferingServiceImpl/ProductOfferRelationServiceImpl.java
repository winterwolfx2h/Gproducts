package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferRelationRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferRelationServiceImpl implements ProductOfferRelationService {

    @Autowired
    ProductOfferRelationRepository ProductOfferRelationRepository;

    @Override
    public ProductOfferRelation create(ProductOfferRelation ProductOfferRelation) {
        return ProductOfferRelationRepository.save(ProductOfferRelation);
    }

    @Override
    public List<ProductOfferRelation> read() {
        return ProductOfferRelationRepository.findAll();
    }

    @Override
    public ProductOfferRelation update(int PoOfferRelation_Code, ProductOfferRelation updatedProductOfferRelation) {
        Optional<ProductOfferRelation> existingProductOptional = ProductOfferRelationRepository.findById(PoOfferRelation_Code);

        if (existingProductOptional.isPresent()) {
            ProductOfferRelation existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProductOfferRelation.getName());
            existingProduct.setValidFor(updatedProductOfferRelation.getValidFor());
            existingProduct.setStatus(updatedProductOfferRelation.getStatus());
            existingProduct.setNumberRelationOfferLowerLimit(updatedProductOfferRelation.getNumberRelationOfferLowerLimit());
            existingProduct.setNumberRelationOfferUpperLimit(updatedProductOfferRelation.getNumberRelationOfferUpperLimit());

            return ProductOfferRelationRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find ProductOfferRelation with ID: " + PoOfferRelation_Code);
        }
    }

    @Override
    public String delete(int PoOfferRelation_Code) {
        ProductOfferRelationRepository.deleteById(PoOfferRelation_Code);
        return ("ProductOfferRelation was successfully deleted");
    }

    @Override
    public ProductOfferRelation findById(int PoOfferRelation_Code) {
        Optional<ProductOfferRelation> optionalPlan = ProductOfferRelationRepository.findById(PoOfferRelation_Code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("ProductOfferRelation with ID " + PoOfferRelation_Code + " not found"));
    }

    @Override
    public List<ProductOfferRelation> searchByKeyword(String name) {
        return ProductOfferRelationRepository.searchByKeyword(name);
    }

    @Override
    public ProductOfferRelation findByName(String name) {
        try {
            Optional<ProductOfferRelation> optionalProductOfferRelation = ProductOfferRelationRepository.findByName(name);
            return optionalProductOfferRelation.orElseThrow(() -> new RuntimeException("ProductOfferRelation with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductOfferRelation");
        }
    }

    @Override
    public boolean existsById(int PoOfferRelation_Code) {
        return ProductOfferRelationRepository.existsById(PoOfferRelation_Code);
    }

}