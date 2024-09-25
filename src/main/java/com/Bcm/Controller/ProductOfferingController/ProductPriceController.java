package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductPriceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Price Controller", description = "All of the Product Price's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductPrice")
@RequiredArgsConstructor
public class ProductPriceController {

  final ProductPriceService productPriceService;
  final ProductRepository productRepository;
  final JdbcTemplate base;

  @PostMapping("/addProductPrice")
  public ResponseEntity<?> createProductPrice(@RequestBody ProductPrice productPrice) {
    try {
      ProductPrice createdProductPrice = productPriceService.create(productPrice);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdProductPrice);
    } catch (DatabaseOperationException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/addProductPrices")
  public ResponseEntity<?> createProductPrices(@RequestBody List<ProductPrice> productPrices) {
    List<ProductPrice> createdProductPrices = new ArrayList<>();
    List<Integer> existingProductPrices = new ArrayList<>();

    for (ProductPrice productPrice : productPrices) {
      if (productPriceService.existsById(productPrice.getProductPriceCode())) {
        existingProductPrices.add(productPrice.getProductPriceCode());
      } else {
        createdProductPrices.addAll(productPriceService.create(Collections.singletonList(productPrice)));
      }
    }

    Map<String, Object> response = new HashMap<>();
    response.put("createdProductPrices", createdProductPrices);
    response.put("existingProductPrices", existingProductPrices);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/searchByProductId")
  public ResponseEntity<?> searchByProductID(@RequestParam Integer productId) {
    try {
      List<ProductPrice> productPricesResponses = productPriceService.findByProductId(productId);

      if (productPricesResponses.isEmpty()) {
        boolean productExists = productRepository.existsById(productId);

        if (!productExists) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body("Product with productId " + productId + " does not exist");
        } else {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No price records found for productId: " + productId);
        }
      }
      return ResponseEntity.ok(productPricesResponses);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error occurred while fetching product prices for productId: " + productId);
    }
  }

  @GetMapping("/listProductPrice")
  public ResponseEntity<List<ProductPrice>> getAllProductPrices() {
    try {
      List<ProductPrice> productPrices = productPriceService.read();
      return ResponseEntity.ok(productPrices);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/{productPriceCode}")
  public ResponseEntity<ProductPrice> getProductPriceById(@PathVariable("productPriceCode") int productPriceCode) {
    try {
      ProductPrice productPrice = productPriceService.findById(productPriceCode);
      return ResponseEntity.ok(productPrice);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PutMapping("/{productPriceCode}")
  public ResponseEntity<?> updateProductPrice(
      @PathVariable("productPriceCode") int productPriceCode, @RequestBody ProductPrice updatedProductPrice) {
    try {
      ProductPrice updatedGroup = productPriceService.update(productPriceCode, updatedProductPrice);
      return ResponseEntity.ok(updatedGroup);
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
  }

  @DeleteMapping("/{productPriceCode}")
  public ResponseEntity<String> deleteProductPrice(@PathVariable("productPriceCode") int productPriceCode) {
    try {
      String resultMessage = productPriceService.delete(productPriceCode);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductPrice>> searchProductPriceByPrice(@RequestParam("price") float price) {
    try {
      List<ProductPrice> searchResults = productPriceService.searchByPrice(price);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
