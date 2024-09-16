package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.RelationResponse;
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

      int productId = productOfferRelation.getProductId();
      ProductOffering productOffering =
          productOfferingRepository
              .findById(productId)
              .orElseThrow(() -> new EntityNotFoundException("ProductOffering not found for Product_id: " + productId));

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
  public ProductOfferRelation findById(Integer id) {
    try {
      Optional<ProductOfferRelation> optionalProductOfferRelation = productOfferRelationRepository.findById(id);
      return optionalProductOfferRelation.orElseThrow(
          () -> new RuntimeException("ProductOfferRelation with ID " + id + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding ProductOfferRelation");
    }
  }

  @Override
  public void deleteById(Integer id) {
    productOfferRelationRepository.deleteById(id);
  }

  @Override
  public List<RelationResponse> searchByProductID(Integer productId) {
    return productOfferRelationRepository.findRelationsByProductId(productId);
  }
}
