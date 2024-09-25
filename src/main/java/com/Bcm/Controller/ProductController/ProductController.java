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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Product Controller", description = "All of the Product's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Product")
public class ProductController {

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
    try {
      List<Product> product = productService.read();
      return ResponseEntity.ok(product);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @GetMapping("/{po_code}")
  public ResponseEntity<Product> getProductById(@PathVariable("po_code") int po_code) {
    Product product = productService.findById(po_code);
    return ResponseEntity.ok(product);
  }

  @GetMapping("/searchProductResDetails")
  public ResponseEntity<Map<String, String>> searchProductResDetails(@RequestParam Integer productId) {
    try {
      Map<String, String> productDetails = productService.fetchProductResourceDetails(productId);
      return ResponseEntity.ok(productDetails);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", error));
    }
  }

  @GetMapping("/searchProductDetails")
  public ResponseEntity<?> searchProductDetails(@RequestParam Integer productId) {
    try {
      List<Map<String, Object>> productDetails = productService.fetchProductDetails(productId);
      return ResponseEntity.ok(productDetails);
    } catch (ProductNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error + e.getMessage());
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
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
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
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @PostMapping("/AddProductDTO")
  public ResponseEntity<?> createProductDTO(@Valid @RequestBody ProductDTO dto) {
    try {

      if (productOfferingService.existsByName(dto.getName())) {
        return ResponseEntity.badRequest().body("A Product with the same name already exists.");
      }

      Product createdProduct = productService.createProductDTO(dto);

      return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);

    } catch (ProductOfferingAlreadyExistsException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An unexpected error occurred while creating the Product . Error: " + e.getMessage());
    }
  }

  @PutMapping("/StockInd/{productId}")
  public ResponseEntity<Product> updateProdStockInd(
      @RequestBody ProductDTO dto, @PathVariable int productId, @RequestParam(required = false) boolean stockInd) {
    try {
      Product product = productService.updateProdStockInd(dto, productId, stockInd);
      return ResponseEntity.ok(product);
    } catch (ProductNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/insertProdDependentCfs")
  @Transactional
  public ResponseEntity<String> insertDependentCfs(@RequestBody List<DependentCfsDto> dependentCfsDtos) {
    if (dependentCfsDtos.isEmpty()) {
      throw new IllegalArgumentException("At least one dependentCfsDtos must be provided");
    }
    for (DependentCfsDto dto : dependentCfsDtos) {
      if (!productRepository.existsById(dto.getProductId())) {
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
    return ResponseEntity.ok("Dependent CFS inserted successfully");
  }

  @PutMapping("update/{productId}")
  public ResponseEntity<?> updateProduct(@RequestBody ProductDTO dto, @PathVariable int productId) {
    try {
      Product updatedProduct = productService.updateProductDTO(dto, productId);
      return ResponseEntity.ok(updatedProduct);
    } catch (ProductNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error + e.getMessage());
    }
  }

  @PostMapping("/insertProductTax")
  @Transactional
  public ResponseEntity<String> insertProductTax(@RequestBody List<ProductTaxDTO> productTaxDTO) {
    if (productTaxDTO.isEmpty()) {
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

    return ResponseEntity.ok("Product Tax inserted successfully");
  }
}
