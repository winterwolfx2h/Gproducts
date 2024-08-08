package com.Bcm.Controller.ProductController;

import com.Bcm.Exception.ProductNotFoundException;
import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.Product.*;
import com.Bcm.Model.ProductOfferingABE.DependentCfsDto;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Product Controller", description = "All of the Product's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/Product")
public class ProductController {

    final JdbcTemplate base;
    final ProductService productService;
    final ProductOfferingService productOfferingService;

    @PersistenceContext
    private EntityManager entityManager;

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
        Map<String, String> response = new HashMap<>();

        // SQL query to check if the product exists
        String checkProductSql = "SELECT COUNT(*) FROM product_offering WHERE product_id = ?";
        int productCount = base.queryForObject(checkProductSql, new Object[]{productId}, Integer.class);

        if (productCount == 0) {
            response.put("message", "Product with the given ID does not exist.");
            return response;
        }

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
                        new Object[]{productId},
                        new RowMapper<Map<String, String>>() {
                            @Override
                            public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
                                Map<String, String> result = new HashMap<>();
                                String physicalResourceName = rs.getString("physical_resource_name");
                                String serviceSpecName = rs.getString("service_spec_name");

                                if (physicalResourceName == null && serviceSpecName == null) {
                                    result.put("message", "No CFS or Physical Resource is associated with that product.");
                                } else {
                                    result.put("physicalResourceName", physicalResourceName);
                                    result.put("serviceSpecName", serviceSpecName);
                                }
                                return result;
                            }
                        });

        return productDetails;
    }

    @GetMapping("/searchProductDetails")
    public ResponseEntity<?> searchProductDetails(@RequestParam Integer productId) {
        try {
            String sql = "SELECT " +
                    "  p.product_id, " +
                    "  c.channel_code AS channelCode, " +
                    "  c.name AS channelName, " +
                    "  e.entity_code AS entityCode, " +
                    "  e.name AS entityName, " +
                    "  pg.product_price_group_code AS productPriceGroupCode, " +
                    "  pg.name AS productPriceGroupName, " +
                    "  p.stock_ind " +
                    "FROM " +
                    "  Product p " +
                    "  LEFT JOIN Product_Channel pc ON p.product_id = pc.product_id " +
                    "  LEFT JOIN Channel c ON pc.channel_code = c.channel_code " +
                    "  LEFT JOIN Product_Entity pe ON p.product_id = pe.product_id " +
                    "  LEFT JOIN Entity e ON pe.entity_code = e.entity_code " +
                    "  LEFT JOIN Product_PriceGroup pp ON p.product_id = pp.product_id " +
                    "  LEFT JOIN Product_Price_Group pg ON pp.product_price_group_code = pg.product_price_group_code " +
                    "WHERE " +
                    "  p.product_id = ?";

            List<Map<String, Object>> productDetails = base.queryForList(sql, new Object[]{productId});

            if (productDetails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }

            return ResponseEntity.ok(productDetails);
        } catch (DataAccessException e) {
            // Handle database-related exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + e.getMessage());
        } catch (RuntimeException e) {
            // Handle runtime exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Runtime error: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
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

    @PostMapping("/AddProductDTO")
    public ResponseEntity<?> createProductDTO(@Valid @RequestBody ProductDTO dto) {
        try {

            // Check for duplicate product by name
            if (productOfferingService.existsByName(dto.getName())) {
                return ResponseEntity.badRequest().body("A Product with the same name already exists.");
            }

            // Convert DTO to entity and save
            Product createdProduct = productService.createProductDTO(dto);

            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);

        } catch (ProductOfferingAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while creating the Product . Error: " + e.getMessage());
        }
    }

    @PutMapping("/StockInd/{productId}")
    public ResponseEntity<Product> updateProdStockInd(
            @RequestBody ProductDTO dto,
            @PathVariable int productId,
            @RequestParam(required = false) boolean stockInd) {
        try {
            Product product = productService.updateProdStockInd(dto, productId, stockInd);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insertProdDependentCfs")
    public ResponseEntity<String> insertDependentCfs(@RequestBody List<DependentCfsDto> dependentCfsDtos) {
        if (dependentCfsDtos.isEmpty()) {
            throw new IllegalArgumentException("At least one dependentCfsDtos must be provided");
        }

        // Vérification de l'existence des product_id dans la table product
        String query = "SELECT COUNT(*) FROM product WHERE product_id = ?";
        for (DependentCfsDto dto : dependentCfsDtos) {
            int count = base.queryForObject(query, new Object[] { dto.getProductId() }, Integer.class);
            if (count == 0) {
                throw new IllegalArgumentException("Product with id " + dto.getProductId() + " does not exist");
            }
        }

        String sql = "INSERT INTO product_depend_cfs (product_id, dependent_cfs) VALUES (?, ?)";

        base.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, dependentCfsDtos.get(i).getProductId());
                ps.setInt(2, dependentCfsDtos.get(i).getDependentCfs());
            }

            @Override
            public int getBatchSize() {
                return dependentCfsDtos.size();
            }
        });

        return ResponseEntity.ok("dependentCfs inserted successfully");
    }
}