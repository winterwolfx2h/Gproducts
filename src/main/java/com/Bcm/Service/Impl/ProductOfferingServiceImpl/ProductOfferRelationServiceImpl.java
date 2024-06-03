package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferRelationRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductOfferRelationServiceImpl implements ProductOfferRelationService {

    final ProductOfferRelationRepository ProductOfferRelationRepository;

    @Override
    public ProductOfferRelation create(ProductOfferRelation ProductOfferRelation) {
        return ProductOfferRelationRepository.save(ProductOfferRelation);
    }

    @Override
    public List<ProductOfferRelation> read() {
        return ProductOfferRelationRepository.findAll();
    }

    @Override
    public ProductOfferRelation update(int pOfferRelationCode, ProductOfferRelation updatedProductOfferRelation) {
        Optional<ProductOfferRelation> existingProductOptional = ProductOfferRelationRepository.findById(pOfferRelationCode);

        if (existingProductOptional.isPresent()) {
            ProductOfferRelation existingProduct = existingProductOptional.get();

            return ProductOfferRelationRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find ProductOfferRelation with ID: " + pOfferRelationCode);
        }
    }

    @Override
    public String delete(int pOfferRelationCode) {
        ProductOfferRelationRepository.deleteById(pOfferRelationCode);
        return ("ProductOfferRelation was successfully deleted");
    }

    @Override
    public ProductOfferRelation findById(int pOfferRelationCode) {
        Optional<ProductOfferRelation> optionalPlan = ProductOfferRelationRepository.findById(pOfferRelationCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("ProductOfferRelation with ID " + pOfferRelationCode + " not found"));
    }

    @Override
    public List<ProductOfferRelation> searchByKeyword(String name) {
        return ProductOfferRelationRepository.searchByKeyword(name);
    }

    @Override
    public ProductOfferRelation findByType(String type) {
        try {
            Optional<ProductOfferRelation> optionalProductOfferRelation = ProductOfferRelationRepository.findByType(type);
            return optionalProductOfferRelation.orElseThrow(() -> new RuntimeException("ProductOfferRelation with Type " + type + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductOfferRelation");
        }
    }

    @Override
    public boolean existsById(int pOfferRelationCode) {
        return ProductOfferRelationRepository.existsById(pOfferRelationCode);
    }

}