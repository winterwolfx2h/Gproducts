package com.Bcm.Controller.ProductController;

import com.Bcm.Exception.ProductNotFoundException;
import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.Product.ProductDTO;
import com.Bcm.Model.Product.ProductTaxDTO;
import com.Bcm.Model.ProductOfferingABE.DependentCfsDto;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.Tax;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Repository.ProductOfferingRepo.TaxRepository;
import com.Bcm.Repository.ServiceConfigRepo.CustomerFacingServiceSpecRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Controller", description = "All of the Product's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Product")
public class ProductController {

  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
  private static final String error = "An unexpected error occurred";
  private static final String DEX = " does not exist";
  private static final String PNF = "Product not found";

  final ProductService productService;
  final ProductRepository productRepository;
  final CustomerFacingServiceSpecRepository cfsRepository;
  final ProductOfferingService productOfferingService;
  final FamilyService familyService;
  final TaxRepository taxRepository;

  @GetMapping("/ProductList")
  public ResponseEntity<?> getAllProduct() {
    logger.info("Fetching all products");
    try {
      List<Product> product = productService.read();
      logger.info("Successfully fetched {} products", product.size());
      return ResponseEntity.ok(product);
    } catch (RuntimeException e) {
      logger.error("Error fetching products: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @GetMapping("/{po_code}")
  public ResponseEntity<Product> getProductById(@PathVariable("po_code") int po_code) {
    logger.info("Fetching product with ID: {}", po_code);
    Product product = productService.findById(po_code);
    logger.info("Successfully fetched product: {}", product.getName());
    return ResponseEntity.ok(product);
  }

  @GetMapping("/searchProductResDetails")
  public ResponseEntity<Map<String, String>> searchProductResDetails(@RequestParam Integer productId) {
    logger.info("Searching product resource details for product ID: {}", productId);
    try {
      Map<String, String> productDetails = productService.fetchProductResourceDetails(productId);
      logger.info("Successfully fetched resource details for product ID: {}", productId);
      return ResponseEntity.ok(productDetails);
    } catch (Exception e) {
      logger.error("Error fetching product resource details: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", error));
    }
  }

  @GetMapping("/searchProductDetails")
  public ResponseEntity<?> searchProductDetails(@RequestParam Integer productId) {
    logger.info("Searching product details for product ID: {}", productId);
    try {
      List<Map<String, Object>> productDetails = productService.fetchProductDetails(productId);
      logger.info("Successfully fetched product details for product ID: {}", productId);
      return ResponseEntity.ok(productDetails);
    } catch (ProductNotFoundException e) {
      logger.warn(PNF, e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      logger.error("Error fetching product details: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error + e.getMessage());
    }
  }

  @GetMapping("/productsWithPOBasicPoType")
  public ResponseEntity<?> getProductsByPOBasicPoType() {
    logger.info("Fetching products with PO basic type");
    try {
      String poType = "PO-Basic";
      List<ProductOffering> productOfferings = productOfferingService.findByPoType(poType);
      if (productOfferings.isEmpty()) {
        logger.warn("No products found for poType: {}", poType);
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

      logger.info("Successfully fetched {} products with PO basic type", products.size());
      return ResponseEntity.ok(products);
    } catch (ResourceNotFoundException e) {
      logger.warn("Resource not found: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      logger.error("Error fetching products with PO basic type: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching products");
    }
  }

  @GetMapping("/searchByFamilyName")
  public ResponseEntity<?> searchProductsByFamilyName(@RequestParam("familyName") String familyName) {
    logger.info("Searching products by family name: {}", familyName);
    List<Product> searchResults = productService.searchByFamilyName(familyName);
    logger.info("Found {} products for family name: {}", searchResults.size(), familyName);
    return ResponseEntity.ok(searchResults);
  }

  @GetMapping("/productsWithPOPLANPoType")
  public ResponseEntity<?> getProductsByPOPlanPoType() {
    logger.info("Fetching products with PO plan type");
    try {
      String poType = "PO-PLAN";
      List<ProductOffering> productOfferings = productOfferingService.findByPoType(poType);
      if (productOfferings.isEmpty()) {
        logger.warn("No products found for poType: {}", poType);
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

      logger.info("Successfully fetched {} products with PO plan type", products.size());
      return ResponseEntity.ok(products);
    } catch (ResourceNotFoundException e) {
      logger.warn("Resource not found: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      logger.error("Error fetching products with PO plan type: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching products");
    }
  }

  @PostMapping("/AddProductDTO")
  public ResponseEntity<?> createProductDTO(@Valid @RequestBody ProductDTO dto) {
    logger.info("Creating product with name: {}", dto.getName());
    try {
      if (productOfferingService.existsByName(dto.getName())) {
        logger.warn("A product with the same name already exists: {}", dto.getName());
        return ResponseEntity.badRequest().body("A Product with the same name already exists.");
      }

      Product createdProduct = productService.createProductDTO(dto);
      logger.info("Product created successfully: {}", createdProduct.getName());
      return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    } catch (ProductOfferingAlreadyExistsException ex) {
      logger.error("Product offering already exists: {}", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    } catch (Exception e) {
      logger.error("Error creating product: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An unexpected error occurred while creating the Product. Error: " + e.getMessage());
    }
  }

  @PutMapping("/StockInd/{productId}")
  public ResponseEntity<Product> updateProdStockInd(
      @RequestBody ProductDTO dto, @PathVariable int productId, @RequestParam(required = false) boolean stockInd) {
    logger.info("Updating stock indicator for product ID: {}", productId);
    try {
      Product product = productService.updateProdStockInd(dto, productId, stockInd);
      logger.info("Successfully updated stock indicator for product ID: {}", productId);
      return ResponseEntity.ok(product);
    } catch (ProductNotFoundException e) {
      logger.warn(PNF, e.getMessage());
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/insertProdDependentCfs")
  @Transactional
  public ResponseEntity<String> insertDependentCfs(@RequestBody List<DependentCfsDto> dependentCfsDtos) {
    logger.info("Inserting dependent CFS for {} product(s)", dependentCfsDtos.size());
    if (dependentCfsDtos.isEmpty()) {
      logger.error("At least one dependent CFS DTO must be provided");
      throw new IllegalArgumentException("At least one dependentCfsDtos must be provided");
    }
    for (DependentCfsDto dto : dependentCfsDtos) {
      if (!productRepository.existsById(dto.getProductId())) {
        logger.error("Product with ID {} does not exist", dto.getProductId());
        throw new IllegalArgumentException("Product with id " + dto.getProductId() + DEX);
      }

      Product product =
          productRepository.findById(dto.getProductId()).orElseThrow(() -> new IllegalArgumentException(PNF));

      CustomerFacingServiceSpec cfs =
          cfsRepository
              .findById(dto.getDependentCfs())
              .orElseThrow(() -> new IllegalArgumentException("Dependent CFS not found"));

      product.getServiceId().add(cfs);
    }
    for (DependentCfsDto dto : dependentCfsDtos) {
      Product product =
          productRepository.findById(dto.getProductId()).orElseThrow(() -> new IllegalArgumentException(PNF));
      productRepository.save(product);
    }
    logger.info("Dependent CFS inserted successfully");
    return ResponseEntity.ok("Dependent CFS inserted successfully");
  }

  @PutMapping("update/{productId}")
  public ResponseEntity<?> updateProduct(@RequestBody ProductDTO dto, @PathVariable int productId) {
    logger.info("Updating product ID: {}", productId);
    try {
      Product updatedProduct = productService.updateProductDTO(dto, productId);
      logger.info("Successfully updated product ID: {}", productId);
      return ResponseEntity.ok(updatedProduct);
    } catch (ProductNotFoundException ex) {
      logger.warn(PNF, ex.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    } catch (Exception e) {
      logger.error("Error updating product: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error + e.getMessage());
    }
  }

  @PostMapping("/insertProductTax")
  @Transactional
  public ResponseEntity<String> insertProductTax(@RequestBody List<ProductTaxDTO> productTaxDTO) {
    logger.info("Inserting product tax for {} tax entries", productTaxDTO.size());
    if (productTaxDTO.isEmpty()) {
      logger.error("At least one Product Tax must be provided");
      throw new IllegalArgumentException("At least one Product_Tax must be provided");
    }
    for (ProductTaxDTO dto : productTaxDTO) {
      Product product =
          productRepository
              .findById(dto.getProductId())
              .orElseThrow(() -> new IllegalArgumentException("Product with id " + dto.getProductId() + DEX));
      Tax tax =
          taxRepository
              .findById(dto.getTaxCode())
              .orElseThrow(() -> new IllegalArgumentException("Tax with tax code " + dto.getTaxCode() + DEX));
      product.getTaxes().add(tax);
    }

    for (ProductTaxDTO dto : productTaxDTO) {
      Product product =
          productRepository.findById(dto.getProductId()).orElseThrow(() -> new IllegalArgumentException(PNF));
      productRepository.save(product);
    }

    logger.info("Product tax inserted successfully");
    return ResponseEntity.ok("Product Tax inserted successfully");
  }
}
