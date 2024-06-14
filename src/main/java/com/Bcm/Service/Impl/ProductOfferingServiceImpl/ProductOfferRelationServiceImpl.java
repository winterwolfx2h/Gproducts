package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.PrimeryKeyProductRelation;
import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferRelationRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOfferRelationServiceImpl implements ProductOfferRelationService {

  final ProductOfferRelationRepository productOfferRelationRepository;
  final ProductOfferingRepository productOfferingRepository;
  final ProductOfferingService productOfferingService;

  @Override
  @Transactional
  public List<ProductOfferRelation> create(List<ProductOfferRelation> productOfferRelations) {
    List<ProductOfferRelation> createdProductOfferRelations = new ArrayList<>();

    for (ProductOfferRelation productOfferRelation : productOfferRelations) {
      createdProductOfferRelations.add(productOfferRelationRepository.save(productOfferRelation));

      // Fetch the ProductOffering by its Product_id
      int productId = productOfferRelation.getId().getProductId();
      ProductOffering productOffering =
          productOfferingRepository
              .findById(productId)
              .orElseThrow(() -> new EntityNotFoundException("ProductOffering not found for Product_id: " + productId));

      // Update the ProductOffering's working step
      productOffering.setWorkingStep("Product Offer Relation");
      productOfferingRepository.save(productOffering);
    }

    return createdProductOfferRelations;
  }

  @Override
  public List<ProductOfferRelation> read() {
    return productOfferRelationRepository.findAll();
  }

  @Override
  public List<ProductOfferRelation> searchByKeyword(String name) {
    return productOfferRelationRepository.searchByKeyword(name);
  }

  @Override
  public ProductOfferRelation findById(PrimeryKeyProductRelation id) {
    try {
      Optional<ProductOfferRelation> optionalProductOfferRelation = productOfferRelationRepository.findById(id);
      return optionalProductOfferRelation.orElseThrow(
          () -> new RuntimeException("ProductOfferRelation with ID " + id + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding ProductOfferRelation");
    }
  }

  @Override
  public void deleteById(PrimeryKeyProductRelation id) {
    productOfferRelationRepository.deleteById(id);
  }

  //    @Override
  //    public ProductOfferRelation update(ProductOfferRelation productOfferRelation) {
  //        PrimeryKeyProductRelation id = productOfferRelation.getId();
  //        Optional<ProductOfferRelation> existingProductOfferRelation = productOfferRelationRepository.findById(id);
  //        if (existingProductOfferRelation.isPresent()) {
  //            ProductOfferRelation updatedProductOfferRelation = existingProductOfferRelation.get();
  //            if (updatedProductOfferRelation.getProduct_id() == productOfferRelation.getProduct_id() &&
  //                    updatedProductOfferRelation.getId().getRelatedProductId() ==
  // productOfferRelation.getId().getRelatedProductId()) {
  //                updatedProductOfferRelation.setType(productOfferRelation.getType());
  //                return productOfferRelationRepository.save(updatedProductOfferRelation);
  //            } else {
  //                throw new RuntimeException("Product with ID " + productOfferRelation.getProduct_id() +
  //                        " is not related to the product with ID " +
  // productOfferRelation.getId().getRelatedProductId());
  //
  //            }
  //        } else {
  //            throw new RuntimeException("ProductOfferRelation with ID " + id + " not found");
  //        }
  //    }

}
