package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductRelation;
import com.Bcm.Repository.ProductOfferingRepo.ProductRelationRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductRelationServiceImpl implements ProductRelationService {

    @Autowired
    ProductRelationRepository ProductRelationRepository;

    @Override
    public ProductRelation create(ProductRelation ProductRelation) {
        return ProductRelationRepository.save(ProductRelation);
    }

    @Override
    public List<ProductRelation> read() {
        return ProductRelationRepository.findAll();
    }

    @Override
    public ProductRelation update(int poRelation_Code, ProductRelation updatedProductRelation) {
        Optional<ProductRelation> existingProductOptional = ProductRelationRepository.findById(poRelation_Code);

        if (existingProductOptional.isPresent()) {
            ProductRelation existingProduct = existingProductOptional.get();
            existingProduct.setType(updatedProductRelation.getType());
            existingProduct.setValidFor(updatedProductRelation.getValidFor());

            return ProductRelationRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find ProductRelation with ID: " + poRelation_Code);
        }
    }

    @Override
    public String delete(int poRelation_Code) {
        ProductRelationRepository.deleteById(poRelation_Code);
        return ("ProductRelation was successfully deleted");
    }

    @Override
    public ProductRelation findById(int poRelation_Code) {
        Optional<ProductRelation> optionalPlan = ProductRelationRepository.findById(poRelation_Code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("ProductRelation with ID " + poRelation_Code + " not found"));
    }




}

