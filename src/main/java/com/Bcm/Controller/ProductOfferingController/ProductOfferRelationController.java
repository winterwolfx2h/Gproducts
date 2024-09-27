package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.NoRelationFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.RelationResponse;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferRelationRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.*;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Offer Relations Controller", description = "All of the Product Offer Relation's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductOfferRelation")
@RequiredArgsConstructor
public class ProductOfferRelationController {

  private static final String MSG = "message";
  private static final String PID = "Product with ID ";
  private static final String AsPl = " has no associated plans";
  private static final String PR = "product_id";
  final ProductOfferRelationService productOfferRelationService;
  final ProductOfferingRepository productOfferingRepository;
  private final ProductOfferRelationRepository productOfferRelationRepository;

  @GetMapping("/searchRelationName")
  public List<Map<String, Object>> searchRelationName(@RequestParam List<Integer> relatedProductIds) {
    if (relatedProductIds.isEmpty()) {
      throw new IllegalArgumentException("At least one relatedProductId must be provided");
    }

    List<RelationResponse> relationResponses =
        productOfferRelationService.searchRelationByRelatedProductIds(relatedProductIds);

    if (relationResponses.isEmpty()) {
      List<Integer> existingProductIds = new ArrayList<>();
      for (ProductOffering productOffering : productOfferingRepository.findAllById(relatedProductIds)) {
        Integer productId = productOffering.getProduct_id();
        existingProductIds.add(productId);
      }

      List<Integer> missingProductIds = new ArrayList<>();
      for (Integer id : relatedProductIds) {
        if (!existingProductIds.contains(id)) {
          missingProductIds.add(id);
        }
      }

      if (!missingProductIds.isEmpty()) {
        throw new EntityNotFoundException("Product IDs not found: " + missingProductIds);
      } else {
        throw new NoRelationFoundException("No relations found for the provided Product IDs");
      }
    }

    List<Map<String, Object>> responseList = new ArrayList<>();
    for (RelationResponse relation : relationResponses) {
      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put(PR, relation.getProduct_id());
      responseMap.put("name", relation.getName());
      responseList.add(responseMap);
    }

    return responseList;
  }

  @GetMapping("/allProductsExceptRelated")
  public List<Map<String, Object>> getAllProductsExceptRelated(@RequestParam Integer selectedProductId) {
    List<RelationResponse> relationResponses =
        productOfferRelationRepository.findAllProductsExceptRelated(selectedProductId);

    List<Map<String, Object>> responseList = new ArrayList<>();
    for (RelationResponse relation : relationResponses) {
      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put(PR, relation.getProduct_id());
      responseMap.put("name", relation.getName());
      responseList.add(responseMap);
    }

    return responseList;
  }

  @GetMapping("/searchPO-PlanByProductId")
  public ResponseEntity<List<RelationResponse>> searchPOPlanByProductId(@RequestParam Integer productId) {
    Integer productCount = productOfferRelationRepository.countByProductId(productId);
    if (productCount == null || productCount == 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Collections.singletonList(new RelationResponse(PID + productId + " does not exist")));
    }
    Integer planCount = productOfferRelationRepository.countPlanAssociations(productId);
    if (planCount == null || planCount == 0) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(Collections.singletonList(new RelationResponse(PID + productId + AsPl)));
    }
    List<RelationResponse> result = productOfferRelationRepository.findPOPlanAssociations(productId);
    if (result.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(Collections.singletonList(new RelationResponse(PID + productId + AsPl)));
    }

    return ResponseEntity.ok(result);
  }

  @GetMapping("/searchMandatoryOpt-ByProductId")
  public ResponseEntity<List<Map<String, Object>>> searchMandatoryOptByProductId(@RequestParam Integer productId) {

    Integer productCount = productOfferRelationRepository.countByProductId(productId);
    if (productCount == null || productCount == 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Collections.singletonList(Map.of(MSG, PID + productId + " does not exist")));
    }

    Integer mandatoryOptCount = productOfferRelationRepository.countMandatoryOptAssociations(productId);
    if (mandatoryOptCount == null || mandatoryOptCount == 0) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(Collections.singletonList(Map.of(MSG, PID + productId + AsPl)));
    }

    List<RelationResponse> result = productOfferRelationRepository.findMandatoryOptionsByProductId(productId);

    if (result.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(Collections.singletonList(Map.of(MSG, PID + productId + " has no associated MandatoryOpts")));
    }

    List<Map<String, Object>> response = new ArrayList<>();
    for (RelationResponse relation : result) {
      Map<String, Object> map = new HashMap<>();
      map.put(PR, relation.getProduct_id());
      map.put("related_product_id", relation.getRelatedProductId());
      map.put("product_name", relation.getName());
      map.put("type", relation.getType());
      response.add(map);
    }

    return ResponseEntity.ok(response);
  }

  @GetMapping("/searchByProductId")
  public List<Map<String, Object>> searchByProductID(@RequestParam Integer productId) {
    Integer productCount = productOfferRelationRepository.countByProductId(productId);
    if (productCount == null || productCount == 0) {
      throw new IllegalArgumentException(PID + productId + " does not exist.");
    }

    List<RelationResponse> result = productOfferRelationRepository.findProductAssociationsByProductId(productId);

    if (result.isEmpty()) {
      throw new IllegalArgumentException(PID + productId + " has no associations.");
    }

    List<Map<String, Object>> response;
    response = new ArrayList<>();
    result.forEach(
        relation -> {
          Map<String, Object> map;
          map = new HashMap<>();
          map.put(PR, relation.getProduct_id());
          map.put("related_product_id", relation.getRelatedProductId());
          map.put("product_name", relation.getName());
          map.put("type", relation.getType());
          response.add(map);
        });

    return response;
  }

  @PostMapping("/addProdOffRelations")
  @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
  public ResponseEntity<List<ProductOfferRelation>> createProductOfferRelations(
      @RequestBody List<ProductOfferRelation> productOfferRelations) {
    List<ProductOfferRelation> createdProductOfferRelations = new ArrayList<>();
    for (ProductOfferRelation productOfferRelation : productOfferRelations) {
      createdProductOfferRelations.addAll(
          productOfferRelationService.create(Collections.singletonList(productOfferRelation)));
    }
    return ResponseEntity.ok(createdProductOfferRelations);
  }

  @GetMapping("/listProdOffrelations")
  @Cacheable(value = "ProdOfferRelationCache")
  public ResponseEntity<?> getAllProductOfferRelations() {
    try {
      List<ProductOfferRelation> productOfferRelations = productOfferRelationService.read();
      return ResponseEntity.ok(productOfferRelations);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/searchByKeyword")
  public List<ProductOfferRelation> searchByKeyword(@RequestParam String name) {
    return productOfferRelationService.searchByKeyword(name);
  }

  @GetMapping("/read")
  public List<ProductOfferRelation> read() {
    return productOfferRelationService.read();
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteProductOfferRelation(@RequestParam Integer id) {
    ProductOfferRelation productOfferRelation = productOfferRelationService.findById(id);

    if (productOfferRelation == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductOfferRelation not found");
    }

    productOfferRelationService.deleteById(id);
    return ResponseEntity.ok("ProductOfferRelation deleted successfully");
  }
}
