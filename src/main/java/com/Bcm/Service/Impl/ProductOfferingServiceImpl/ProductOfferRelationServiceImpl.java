package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.PrimeryKeyProductRelation;
import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferRelationRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductOfferRelationServiceImpl implements ProductOfferRelationService {

    final ProductOfferRelationRepository ProductOfferRelationRepository;
    private final ProductOfferRelationRepository productOfferRelationRepository;

    @Override
    public List<ProductOfferRelation> create(List<ProductOfferRelation> productOfferRelations) {
        List<ProductOfferRelation> createdProductOfferRelations = new ArrayList<>();
        for (ProductOfferRelation productOfferRelation : productOfferRelations) {
            createdProductOfferRelations.add(productOfferRelationRepository.save(productOfferRelation));
        }
        return createdProductOfferRelations;
    }

    @Override
    public List<ProductOfferRelation> read() {
        return ProductOfferRelationRepository.findAll();
    }

    @Override
    public List<ProductOfferRelation> searchByKeyword(String name) {
        return ProductOfferRelationRepository.searchByKeyword(name);
    }

    @Override
    public ProductOfferRelation findById(PrimeryKeyProductRelation id) {
        try {
            Optional<ProductOfferRelation> optionalProductOfferRelation = ProductOfferRelationRepository.findById(id);
            return optionalProductOfferRelation.orElseThrow(() -> new RuntimeException("ProductOfferRelation with ID " + id + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding ProductOfferRelation");
        }
    }

    @Override
    public void deleteById(PrimeryKeyProductRelation id) {
        ProductOfferRelationRepository.deleteById(id);
    }

//    @Override
//    public ProductOfferRelation update(ProductOfferRelation productOfferRelation) {
//        PrimeryKeyProductRelation id = productOfferRelation.getId();
//        Optional<ProductOfferRelation> existingProductOfferRelation = productOfferRelationRepository.findById(id);
//        if (existingProductOfferRelation.isPresent()) {
//            ProductOfferRelation updatedProductOfferRelation = existingProductOfferRelation.get();
//            if (updatedProductOfferRelation.getProduct_id() == productOfferRelation.getProduct_id() &&
//                    updatedProductOfferRelation.getId().getRelatedProductId() == productOfferRelation.getId().getRelatedProductId()) {
//                updatedProductOfferRelation.setType(productOfferRelation.getType());
//                return productOfferRelationRepository.save(updatedProductOfferRelation);
//            } else {
//                throw new RuntimeException("Product with ID " + productOfferRelation.getProduct_id() +
//                        " is not related to the product with ID " + productOfferRelation.getId().getRelatedProductId());
//
//            }
//        } else {
//            throw new RuntimeException("ProductOfferRelation with ID " + id + " not found");
//        }
//    }


}