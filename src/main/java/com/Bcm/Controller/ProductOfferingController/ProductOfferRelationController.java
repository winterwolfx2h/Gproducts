package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.NoRelationFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.RelationResponse;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
  private static final String query = "SELECT COUNT(*) FROM product WHERE product_id = ?";
  final JdbcTemplate base;
  final ProductOfferRelationService productOfferRelationService;

  @GetMapping("/searchRelationName")
  public List<RelationResponse> searchRelationName(@RequestParam List<Integer> relatedProductIds) {
    if (relatedProductIds.isEmpty()) {
      throw new IllegalArgumentException("At least one relatedProductId must be provided");
    }

    String placeholders = relatedProductIds.stream().map(id -> "?").collect(Collectors.joining(", "));

    String sqlRelation =
        "SELECT distinct por.product_id, por.related_product_id, po.name, poff.po_type "
            + "FROM public.product_offer_relation por "
            + "JOIN public.product po ON po.product_id = por.product_id "
            + "JOIN public.product_offering poff ON poff.product_id = por.product_id "
            + "AND por.related_product_id IN ("
            + placeholders
            + ")"
            + "ORDER BY por.product_id";

    Object[] params = relatedProductIds.toArray();

    List<RelationResponse> relationResponses =
        base.query(sqlRelation, params, new BeanPropertyRowMapper<>(RelationResponse.class));

    if (relationResponses.isEmpty()) {
      String sqlProductCheck = "SELECT product_id FROM public.product WHERE product_id IN (" + placeholders + ")";
      List<Integer> existingProductIds = base.queryForList(sqlProductCheck, params, Integer.class);

      List<Integer> missingProductIds =
          relatedProductIds.stream().filter(id -> !existingProductIds.contains(id)).toList();

      if (!missingProductIds.isEmpty()) {
        throw new EntityNotFoundException("Product IDs not found: " + missingProductIds);
      } else {
        throw new NoRelationFoundException("No relations found for the provided Product IDs");
      }
    }
    return relationResponses;
  }

  @GetMapping("/allProductsExceptRelated")
  public List<RelationResponse> getAllProductsExceptRelated(@RequestParam Integer selectedProductId) {
    String sqlRelation =
        "SELECT distinct p.product_id, p.name, poff.po_type "
            + "FROM public.product p "
            + "JOIN public.product_offering poff ON poff.product_id = p.product_id "
            + "WHERE poff.po_type <> 'PO-Plan' "
            + "AND p.product_id NOT IN ("
            + "  SELECT distinct por.related_product_id "
            + "  FROM public.product_offer_relation por "
            + "  WHERE por.product_id =?"
            + ") "
            + "AND p.product_id IN ("
            + "  SELECT distinct por.product_id "
            + "  FROM public.product_offer_relation por "
            + "  WHERE por.related_product_id = ("
            + "    SELECT distinct por2.related_product_id "
            + "    FROM public.product_offer_relation por2 "
            + "    WHERE por2.product_id =? "
            + "    AND por2.type = 'Plan'"
            + "  ) "
            + "  AND por.type = 'Plan'"
            + ") "
            + "ORDER BY p.product_id";

    Object[] params = new Object[] {selectedProductId, selectedProductId};

    List<RelationResponse> relationResponses =
        base.query(sqlRelation, params, new BeanPropertyRowMapper<>(RelationResponse.class));

    return relationResponses;
  }

  @GetMapping("/searchPO-PlanByProductId")
  public ResponseEntity<List<Map<String, Object>>> searchPOPlanByProductId(@RequestParam Integer productId) {

    String checkProductQuery = query;
    Integer productCount = base.queryForObject(checkProductQuery, new Object[] {productId}, Integer.class);

    if (productCount == null || productCount == 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Collections.singletonList(Map.of(MSG, PID + productId + " does not exist")));
    }

    String checkPlanAssociationQuery =
        "SELECT COUNT(*) FROM product_offer_relation WHERE product_id = ? AND type = 'Plan'";
    Integer planCount = base.queryForObject(checkPlanAssociationQuery, new Object[] {productId}, Integer.class);

    if (planCount == null || planCount == 0) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(Collections.singletonList(Map.of(MSG, PID + productId + AsPl)));
    }

    String sqlSearchByProductId =
        "SELECT distinct p.product_id, po.name AS product_name,po.family_name, po. sub_family, p.related_product_id,"
            + " p.type, p.sub_Type, pofo.markets, pofo.submarkets FROM product_offer_relation p JOIN product po ON"
            + " p.related_product_id = po.product_id JOIN product_offering pofo ON po.product_id = pofo.product_id"
            + " WHERE p.product_id = ? and type ='Plan' ";

    List<Map<String, Object>> result =
        base.query(
            sqlSearchByProductId,
            new Object[] {productId},
            new RowMapper<Map<String, Object>>() {
              @Override
              public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> response = new HashMap<>();
                response.put("product_id", rs.getInt("product_id"));
                response.put("related_product_id", rs.getInt("related_product_id"));
                response.put("product_name", rs.getString("product_name"));
                response.put("type", rs.getString("type"));
                response.put("sub_Type", rs.getString("sub_Type"));
                response.put("family_name", rs.getString("family_name"));
                response.put("sub_family", rs.getString("sub_family"));
                response.put("markets", rs.getString("markets"));
                response.put("submarkets", rs.getString("submarkets"));
                return response;
              }
            });

    if (result.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(Collections.singletonList(Map.of(MSG, PID + productId + AsPl)));
    }
    return ResponseEntity.ok(result);
  }

  @GetMapping("/searchMandatoryOpt-ByProductId")
  public ResponseEntity<List<Map<String, Object>>> searchMandatoryOptByProductId(@RequestParam Integer productId) {

    String checkProductQuery = query;
    Integer productCount = base.queryForObject(checkProductQuery, new Object[] {productId}, Integer.class);

    if (productCount == null || productCount == 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Collections.singletonList(Map.of(MSG, PID + productId + " does not exist")));
    }

    String checkMandatoryOptAssociationQuery =
        "SELECT COUNT(*) FROM product_offer_relation WHERE (product_id = ?) AND (type = 'AutoInclude' OR type ="
            + " 'Optional')";
    Integer MandatoryOptCount =
        base.queryForObject(checkMandatoryOptAssociationQuery, new Object[] {productId}, Integer.class);

    if (MandatoryOptCount == null || MandatoryOptCount == 0) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(Collections.singletonList(Map.of(MSG, PID + productId + AsPl)));
    }

    String sqlSearchByProductId =
        "SELECT distinct p.product_id, po.name AS product_name, p.related_product_id, p.type "
            + "FROM product_offer_relation p "
            + "JOIN product po ON p.related_product_id = po.product_id "
            + "WHERE (p.product_id = ?) AND (type = 'AutoInclude' OR type = 'Optional')";

    List<Map<String, Object>> result =
        base.query(
            sqlSearchByProductId,
            new Object[] {productId},
            new RowMapper<Map<String, Object>>() {
              @Override
              public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> response = new HashMap<>();
                response.put("product_id", rs.getInt("product_id"));
                response.put("related_product_id", rs.getInt("related_product_id"));
                response.put("product_name", rs.getString("product_name"));
                response.put("type", rs.getString("type"));
                return response;
              }
            });

    if (result.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(Collections.singletonList(Map.of(MSG, PID + productId + " has no associated MandatoryOpts")));
    }
    return ResponseEntity.ok(result);
  }

  @GetMapping("/searchByProductId")
  public List<Map<String, Object>> searchByProductID(@RequestParam Integer productId) {
    String sqlCheckProductId = query;
    int count = base.queryForObject(sqlCheckProductId, Integer.class, productId);

    if (count == 0) {
      throw new IllegalArgumentException(PID + productId + " does not exist.");
    }

    String sqlSearchByProductId =
        "SELECT distinct por.product_id AS product_id, por.related_product_id AS related_product_id, po.name AS"
            + " product_name, por.type FROM product_offer_relation por JOIN product po ON por.related_product_id ="
            + " po.product_id WHERE por.product_id = ? AND por.type NOT LIKE 'Plan'";

    List<Map<String, Object>> result =
        base.query(
            sqlSearchByProductId,
            new Object[] {productId},
            new RowMapper<Map<String, Object>>() {
              @Override
              public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> response = new HashMap<>();
                response.put("product_id", rs.getInt("product_id"));
                response.put("related_product_id", rs.getInt("related_product_id"));
                response.put("product_name", rs.getString("product_name"));
                response.put("type", rs.getString("type"));
                return response;
              }
            });

    if (result.isEmpty()) {
      throw new IllegalArgumentException(PID + productId + " has no associations.");
    }

    return result;
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
