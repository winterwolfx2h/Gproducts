package com.Bcm.Controller.ProductController;

import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Controller", description = "All of the Product's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/Product")
public class ProductController {

  final JdbcTemplate base;
  final ProductService productService;
  final ProductOfferingService productOfferingService;
  @PersistenceContext private EntityManager entityManager;

  @GetMapping("/ProductList")
  @Cacheable(value = "productCache")
  public ResponseEntity<?> getAllProduct() {
    try {
      List<Product> product = productService.read();
      return ResponseEntity.ok(product);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/searchProductResDetails")
  public Map<String, String> searchProductResDetails(@RequestParam Integer productId) {
    // SQL query to get the names of the PhysicalResource and CustomerFacingServiceSpec for the specified product_id
    String sql =
        "SELECT pr.name AS physical_resource_name, cfss.name AS service_spec_name "
            + "FROM product_offering po "
            + "LEFT JOIN physical_resource pr ON po.pr_id = pr.pr_id "
            + "LEFT JOIN customer_facing_service_spec cfss ON po.service_id = cfss.service_id "
            + "WHERE po.product_id = ?";

    // Execute the query and map the result set to a Map
    Map<String, String> productDetails;
    productDetails =
        base.queryForObject(
            sql,
            new Object[] {productId},
            new RowMapper<Map<String, String>>() {
              @Override
              public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, String> result = new HashMap<>();
                result.put("physicalResourceName", rs.getString("physical_resource_name"));
                result.put("serviceSpecName", rs.getString("service_spec_name"));
                return result;
              }
            });

    return productDetails;
  }

  @GetMapping("/searchProductDetails")
  public ResponseEntity<ProductDetailsResponse> searchProductDetails(@RequestParam Integer productId) {
    String jpqlQuery =
        "SELECT ch.name AS channelName, ee.name AS entityName, ppg.name AS productPriceGroupName "
            + "FROM Product p "
            + "JOIN p.channelCode ch "
            + "JOIN p.entityCode ee "
            + "JOIN p.productPriceGroups ppg "
            + "WHERE p.Product_id = :productId";

    TypedQuery<Object[]> query = entityManager.createQuery(jpqlQuery, Object[].class);
    query.setParameter("productId", productId);

    List<Object[]> results = query.getResultList();

    if (results.isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      Object[] result = results.get(0);
      String channelName = (String) result[0];
      String entityName = (String) result[1];
      String productPriceGroupName = (String) result[2];

      ProductDetailsResponse response = new ProductDetailsResponse(channelName, entityName, productPriceGroupName);
      return ResponseEntity.ok(response);
    }
  }

  @GetMapping("/productsWithPOBasicPoType")
  public ResponseEntity<?> getProductsByPOBasicPoType() {
    try {
      String poType = "PO-Basic";
      List<ProductOffering> productOfferings = productOfferingService.findByPoType(poType);

      if (productOfferings.isEmpty()) {
        throw new ResourceNotFoundException("No products found for poType: " + poType);
      }

      List<Object> products =
          productOfferings.stream()
              .map(
                  productOffering -> {
                    Product product = productOffering.convertToProduct();
                    ProductOffering productOfferingDTO = new ProductOffering();
                    productOfferingDTO.setProduct_id(product.getProduct_id());
                    productOfferingDTO.setName(product.getName());
                    productOfferingDTO.setEffectiveFrom(product.getEffectiveFrom());
                    productOfferingDTO.setEffectiveTo(product.getEffectiveTo());
                    productOfferingDTO.setDescription(product.getDescription());
                    productOfferingDTO.setDetailedDescription(product.getDetailedDescription());
                    productOfferingDTO.setPoType(productOffering.getPoType());
                    productOfferingDTO.setFamilyName(product.getFamilyName());
                    productOfferingDTO.setSubFamily(product.getSubFamily());
                    productOfferingDTO.setParent(productOffering.getParent());
                    productOfferingDTO.setStatus(productOffering.getStatus());
                    productOfferingDTO.setCategory(productOffering.getCategory());
                    productOfferingDTO.setPoParent_Child(productOffering.getPoParent_Child());
                    productOfferingDTO.setMarkets(productOffering.getMarkets());
                    productOfferingDTO.setSubmarkets(productOffering.getSubmarkets());
                    productOfferingDTO.setBS_externalId(productOffering.getBS_externalId());
                    productOfferingDTO.setCS_externalId(productOffering.getCS_externalId());

                    return productOfferingDTO;
                  })
              .collect(Collectors.toList());

      return ResponseEntity.ok(products);
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/searchByFamilyName")
  public ResponseEntity<?> searchProductsByFamilyName(@RequestParam("familyName") String familyName) {
    List<Product> searchResults = productService.searchByFamilyName(familyName);
    return ResponseEntity.ok(searchResults);
  }

  @GetMapping("/productsWithPOPLANPoType")
  public ResponseEntity<?> getProductsByPOPlanPoType() {
    try {
      String poType = "PO-PLAN";
      List<ProductOffering> productOfferings = productOfferingService.findByPoType(poType);

      if (productOfferings.isEmpty()) {
        throw new ResourceNotFoundException("No products found for poType: " + poType);
      }

      List<Object> products =
          productOfferings.stream()
              .map(
                  productOffering -> {
                    Product product = productOffering.convertToProduct();
                    ProductOffering productOfferingDTO = new ProductOffering();
                    productOfferingDTO.setProduct_id(product.getProduct_id());
                    productOfferingDTO.setName(product.getName());
                    productOfferingDTO.setEffectiveFrom(product.getEffectiveFrom());
                    productOfferingDTO.setEffectiveTo(product.getEffectiveTo());
                    productOfferingDTO.setDescription(product.getDescription());
                    productOfferingDTO.setDetailedDescription(product.getDetailedDescription());
                    productOfferingDTO.setPoType(productOffering.getPoType());
                    productOfferingDTO.setFamilyName(product.getFamilyName());
                    productOfferingDTO.setSubFamily(product.getSubFamily());
                    productOfferingDTO.setParent(productOffering.getParent());
                    productOfferingDTO.setStatus(productOffering.getStatus());
                    productOfferingDTO.setMarkets(productOffering.getMarkets());
                    productOfferingDTO.setSubmarkets(productOffering.getSubmarkets());

                    return productOfferingDTO;
                  })
              .collect(Collectors.toList());

      return ResponseEntity.ok(products);
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }
}
