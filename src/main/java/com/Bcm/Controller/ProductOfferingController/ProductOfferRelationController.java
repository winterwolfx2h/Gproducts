package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.NoRelationFoundException;
import com.Bcm.Model.ProductOfferingABE.PrimeryKeyProductRelation;
import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.RelationResponse;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
<<<<<<< src/main/java/com/Bcm/Controller/ProductOfferingController/ProductOfferRelationController.java

import java.sql.ResultSet;
import java.sql.SQLException;
=======
import java.sql.ResultSet;
import java.sql.SQLException;
import io.swagger.v3.oas.annotations.tags.Tag;
>>>>>>> src/main/java/com/Bcm/Controller/ProductOfferingController/ProductOfferRelationController.java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Offer Relations Controller", description = "All of the Product Offer Relation's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductOfferRelation")
@RequiredArgsConstructor
public class ProductOfferRelationController {

  final JdbcTemplate base;
  final ProductOfferRelationService productOfferRelationService;

  @GetMapping("/searchRelationName")
  public List<RelationResponse> searchRelationName(@RequestParam List<Integer> relatedProductIds) {
    if (relatedProductIds.isEmpty()) {
      throw new IllegalArgumentException("At least one relatedProductId must be provided");
    }

    // Construct placeholders for the IN clause
    String placeholders = relatedProductIds.stream().map(id -> "?").collect(Collectors.joining(", "));

    String sqlRelation =
        "SELECT por.product_id, por.related_product_id, po.name, poff.po_type "
            + "FROM public.product_offer_relation por "
            + "JOIN public.product po ON po.product_id = por.product_id "
            + "JOIN public.product_offering poff ON poff.product_id = por.product_id "
            + "AND por.related_product_id IN ("
            + placeholders
            + ")"
            + "ORDER BY por.product_id";

    // Convert List<Integer> to Object[] for query parameters
    Object[] params = relatedProductIds.toArray();

    List<RelationResponse> relationResponses =
        base.query(sqlRelation, params, new BeanPropertyRowMapper<>(RelationResponse.class));

    if (relationResponses.isEmpty()) {
      // Check if the provided IDs exist in the product table
      String sqlProductCheck = "SELECT product_id FROM public.product WHERE product_id IN (" + placeholders + ")";
      List<Integer> existingProductIds = base.queryForList(sqlProductCheck, params, Integer.class);

      // Determine missing product IDs
      List<Integer> missingProductIds =
          relatedProductIds.stream().filter(id -> !existingProductIds.contains(id)).collect(Collectors.toList());

      if (!missingProductIds.isEmpty()) {
        throw new EntityNotFoundException("Product IDs not found: " + missingProductIds);
      } else {
        throw new NoRelationFoundException("No relations found for the provided Product IDs");
      }
    }

    return relationResponses;
  }


  @GetMapping("/searchByProductId")
  public List<RelationResponse> searchByProductID(@RequestParam Integer productId) {

    String sqlSearchByProductId = "SELECT p.product_id,po.name " +
            "FROM product_offer_relation p " +
            "JOIN public.product po ON p.related_product_id = po.product_id " +
            "WHERE p.product_id = ? AND p.type = 'Plan'";


    List<RelationResponse> relationResponses = base.query(
            sqlSearchByProductId,
            new Object[]{productId},
            new RowMapper<RelationResponse>() {
              @Override
              public RelationResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                RelationResponse response = new RelationResponse();
                response.setProduct_id(rs.getInt("product_id"));
                response.setName(rs.getString("name"));
                return response;
              }
            });

    return relationResponses;
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

  @GetMapping("/search")
  public ResponseEntity<?> searchProductOfferRelationsByKeyword(@RequestParam("type") String type) {
    try {
      List<ProductOfferRelation> searchResults = productOfferRelationService.searchByKeyword(type);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/findById")
  public ResponseEntity<?> findProductOfferRelationById(@RequestParam("id") PrimeryKeyProductRelation id) {
    try {
      ProductOfferRelation productOfferRelation = productOfferRelationService.findById(id);
      return ResponseEntity.ok(productOfferRelation);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @DeleteMapping("/deleteById")
  @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
  public ResponseEntity<?> deleteProductOfferRelationById(@RequestParam("id") PrimeryKeyProductRelation id) {
    try {
      productOfferRelationService.deleteById(id);
      return ResponseEntity.ok("ProductOfferRelation deleted successfully");
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
  public void invalidateProdOfferRelationCache() {}
}
