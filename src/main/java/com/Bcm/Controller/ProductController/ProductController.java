package com.Bcm.Controller.ProductController;

import com.Bcm.Exception.ProductNotFoundException;
import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.Product.ProductDTO;
import com.Bcm.Model.Product.ProductTaxDTO;
import com.Bcm.Model.ProductOfferingABE.DependentCfsDto;
import com.Bcm.Model.ProductOfferingABE.Tax;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Repository.ProductOfferingRepo.TaxRepository;
import com.Bcm.Repository.ServiceConfigRepo.CustomerFacingServiceSpecRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
    logger.info("Request received to fetch all products");
    try {
      List<Product> products = productService.read();
      logger.info("Successfully fetched {} products", products.size());
      return ResponseEntity.ok(products);
    } catch (RuntimeException e) {
      logger.error("Error fetching products: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @GetMapping("/{po_code}")
  public ResponseEntity<?> getProductById(@PathVariable("po_code") int po_code) {
    logger.info("Fetching product with ID: {}", po_code);
      Product product = productService.findById(po_code);
      logger.info("Successfully fetched product: {}", product.getName());
      return ResponseEntity.ok(product);
  }

  @GetMapping("/searchProductDetails")
  public ResponseEntity<?> searchProductDetails(@RequestParam Integer productId) {
    logger.info("Searching for product details with ID: {}", productId);
    try {
      List<Map<String, Object>> productDetails = productService.fetchProductDetails(productId);
      logger.info("Successfully fetched details for product ID: {}", productId);
      return ResponseEntity.ok(productDetails);
    } catch (ProductNotFoundException e) {
      logger.error("Product not found with ID: {}", productId);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      logger.error("Error fetching product details: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @GetMapping("/searchByFamilyName")
  public ResponseEntity<?> searchProductsByFamilyName(@RequestParam("familyName") String familyName) {
    logger.info("Searching products by family name: {}", familyName);
    List<Product> searchResults = productService.searchByFamilyName(familyName);
    logger.info("Found {} products for family name: {}", searchResults.size(), familyName);
    return ResponseEntity.ok(searchResults);
  }

  @PostMapping("/AddProductDTO")
  public ResponseEntity<?> createProductDTO(@Valid @RequestBody ProductDTO dto) {
    logger.info("Creating a new Product DTO with name: {}", dto.getName());
    try {
      if (productService.existsByName(dto.getName())) {
        logger.warn("Conflicted product name: {}", dto.getName());
        return ResponseEntity.badRequest().body("A Product with the same name already exists.");
      }

      Product createdProduct = productService.createProductDTO(dto);
      logger.info("Successfully created product: {}", createdProduct.getName());
      return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);

    } catch (ProductOfferingAlreadyExistsException ex) {
      logger.error("Attempted to create a product that already exists: {}", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    } catch (Exception e) {
      logger.error("Error creating product: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An unexpected error occurred while creating the Product.");
    }
  }

  @PutMapping("/StockInd/{productId}")
  public ResponseEntity<Product> updateProdStockInd(
          @RequestBody ProductDTO dto, @PathVariable int productId, @RequestParam(required = false) boolean stockInd) {
    logger.info("Updating stock indicator for product ID: {} with stockInd: {}", productId, stockInd);
    try {
      Product product = productService.updateProdStockInd(dto, productId, stockInd);
      logger.info("Successfully updated stock indicator for product ID: {}", productId);
      return ResponseEntity.ok(product);
    } catch (ProductNotFoundException e) {
      logger.error("Product not found for update with ID: {}", productId);
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/insertProdDependentCfs")
  @Transactional
  public ResponseEntity<String> insertDependentCfs(@RequestBody List<DependentCfsDto> dependentCfsDtos) {
    logger.info("Inserting dependent CFS for products");
    if (dependentCfsDtos.isEmpty()) {
      logger.error("At least one dependent CFS must be provided");
      throw new IllegalArgumentException("At least one dependent CFS must be provided");
    }
    for (DependentCfsDto dto : dependentCfsDtos) {
      if (!productRepository.existsById(dto.getProductId())) {
        logger.error("Product with ID {} does not exist", dto.getProductId());
        throw new IllegalArgumentException("Product with id " + dto.getProductId() + DEX);
      }
      Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new IllegalArgumentException(PNF));
      CustomerFacingServiceSpec cfs = cfsRepository.findById(dto.getDependentCfs())
              .orElseThrow(() -> new IllegalArgumentException("Dependent CFS not found"));
      product.getServiceId().add(cfs);
      logger.info("Added dependent CFS to product ID: {}", dto.getProductId());
    }
    for (DependentCfsDto dto : dependentCfsDtos) {
      Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new IllegalArgumentException(PNF));
      productRepository.save(product);
    }
    logger.info("Successfully inserted dependent CFS");
    return ResponseEntity.ok("Dependent CFS inserted successfully");
  }

  @Transactional
  @PutMapping("update/{productId}")
  public ResponseEntity<?> updateProduct(@RequestBody ProductDTO dto, @PathVariable int productId) {
    logger.info("Updating product with ID: {}", productId);
    try {
      Product updatedProduct = productService.updateProductDTO(dto, productId);
      logger.info("Successfully updated product ID: {}", productId);
      return ResponseEntity.ok(updatedProduct);
    } catch (ProductNotFoundException ex) {
      logger.error("Product not found for update: {}", ex.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    } catch (Exception e) {
      logger.error("Error updating product: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @PostMapping("/insertProductTax")
  @Transactional
  public ResponseEntity<String> insertProductTax(@RequestBody List<ProductTaxDTO> productTaxDTO) {
    logger.info("Inserting product tax information");
    if (productTaxDTO.isEmpty()) {
      logger.error("At least one Product_Tax must be provided");
      throw new IllegalArgumentException("At least one Product_Tax must be provided");
    }
    for (ProductTaxDTO dto : productTaxDTO) {
      Product product = productRepository.findById(dto.getProductId())
              .orElseThrow(() -> new IllegalArgumentException("Product with id " + dto.getProductId() + DEX));

      Tax tax = taxRepository.findById(dto.getTaxCode())
              .orElseThrow(() -> new IllegalArgumentException("Tax with tax code " + dto.getTaxCode() + DEX));

      product.getTaxes().add(tax);
      logger.info("Added tax to product ID: {}", dto.getProductId());
    }

    for (ProductTaxDTO dto : productTaxDTO) {
      Product product = productRepository.findById(dto.getProductId())
              .orElseThrow(() -> new IllegalArgumentException(PNF));
      productRepository.save(product);
    }

    logger.info("Successfully inserted product tax");
    return ResponseEntity.ok("Product Tax inserted successfully");
  }
}