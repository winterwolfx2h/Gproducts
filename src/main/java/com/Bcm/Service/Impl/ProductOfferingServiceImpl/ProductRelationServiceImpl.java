package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductRelation;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductRelationRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductRelationService;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductRelationServiceImpl implements ProductRelationService {

  final ProductRelationRepository ProductRelationRepository;
  final ProductOfferingRepository productOfferingRepository;

  @Override
  @Transactional
  public ProductRelation create(ProductRelation productRelation) {

    // Fetch the ProductOffering by its Product_id
    int productId = productRelation.getProductId();
    ProductOffering productOffering =
        productOfferingRepository
            .findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("ProductOffering not found for Product_id: " + productId));

    // Update the ProductOffering's working step
    productOffering.setWorkingStep("Product Relation");
    productOfferingRepository.save(productOffering);

    // Save the ProductRelation
    return ProductRelationRepository.save(productRelation);
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
    return optionalPlan.orElseThrow(
        () -> new RuntimeException("ProductRelation with ID " + poRelation_Code + " not found"));
  }

  @Override
  public List<ProductRelation> searchByKeyword(String type) {
    return ProductRelationRepository.searchByKeyword(type);
  }

  @Override
  public ProductRelation findByType(String type) {
    try {
      Optional<ProductRelation> optionalProductRelation = ProductRelationRepository.findByType(type);
      return optionalProductRelation.orElseThrow(
          () -> new RuntimeException("ProductRelation with type " + type + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding ProductRelation");
    }
  }

  @Override
  public boolean existsById(int poRelation_Code) {
    return ProductRelationRepository.existsById(poRelation_Code);
  }
}
