package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ProductPriceVersionAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductPriceVersion;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductPriceVersionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductPriceVersion")
@RequiredArgsConstructor
public class ProductPriceVersionController {

  final ProductPriceVersionService productPriceVersionService;

  @PostMapping("/addProductPriceVersion")
  public ResponseEntity<?> createType(@RequestBody ProductPriceVersion productPriceVersion) {
    try {
      ProductPriceVersion createdProductPriceVersion = productPriceVersionService.create(productPriceVersion);
      return ResponseEntity.ok(createdProductPriceVersion);
    } catch (ProductPriceVersionAlreadyExistsException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/listProductPriceVersion")
  public ResponseEntity<List<ProductPriceVersion>> getAllProductPriceVersions() {
    try {
      List<ProductPriceVersion> ProductPriceVersions = productPriceVersionService.read();
      return ResponseEntity.ok(ProductPriceVersions);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/{productPriceVersionCode}")
  public ResponseEntity<ProductPriceVersion> getProductPriceVersionById(
      @PathVariable("productPriceVersionCode") int productPriceVersionCode) {
    try {
      ProductPriceVersion productPriceVersion = productPriceVersionService.findById(productPriceVersionCode);
      return ResponseEntity.ok(productPriceVersion);
    } catch (RuntimeException e) {
      return ResponseEntity.status(404).body(null);
    }
  }

  @PutMapping("/{productPriceVersionCode}")
  public ResponseEntity<?> updateProductPriceVersion(
      @PathVariable("productPriceVersionCode") int productPriceVersionCode,
      @RequestBody ProductPriceVersion updatedProductPriceVersion) {
    try {
      ProductPriceVersion updatedVersion =
          productPriceVersionService.update(productPriceVersionCode, updatedProductPriceVersion);
      return ResponseEntity.ok(updatedVersion);
    } catch (ProductPriceVersionAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
  }

  @DeleteMapping("/{productPriceVersionCode}")
  public ResponseEntity<String> deleteProductPriceVersion(
      @PathVariable("productPriceVersionCode") int productPriceVersionCode) {
    try {
      String resultMessage = productPriceVersionService.delete(productPriceVersionCode);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductPriceVersion>> searchProductPriceVersionsByKeyword(
      @RequestParam("name") String name) {
    try {
      List<ProductPriceVersion> searchResults = productPriceVersionService.searchByKeyword(name);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }
}
