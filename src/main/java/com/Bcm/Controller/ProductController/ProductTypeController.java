package com.Bcm.Controller.ProductController;

import com.Bcm.Model.Product.ProductType;
import com.Bcm.Service.Srvc.ProductSrvc.ProductTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ProductType Controller", description = "All of the Type's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductType")
public class ProductTypeController {

  final ProductTypeService productTypeService;

  @PostMapping("/addProductType")
  @CacheEvict(value = "TypesCache", allEntries = true)
  public ResponseEntity<?> create(@RequestBody ProductType type) {
    try {
      ProductType createdType = productTypeService.create(type);
      return ResponseEntity.ok(createdType);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/listProductTypes")
  @Cacheable(value = "TypesCache")
  public ResponseEntity<?> getAllProductTypes() {
    try {
      List<ProductType> types = productTypeService.read();
      return ResponseEntity.ok(types);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/{productTypeCode}")
  public ResponseEntity<?> getTypeById(@PathVariable("productTypeCode") int productTypeCode) {
    try {
      ProductType ProductType = productTypeService.findById(productTypeCode);
      return ResponseEntity.ok(ProductType);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @PutMapping("/{productTypeCode}")
  @CacheEvict(value = "TypesCache", allEntries = true)
  public ResponseEntity<?> updateType(
      @PathVariable("productTypeCode") int productTypeCode, @RequestBody ProductType updatedProductType) {
    try {
      ProductType updatedGroup = productTypeService.update(productTypeCode, updatedProductType);
      return ResponseEntity.ok(updatedGroup);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @DeleteMapping("/{productTypeCode}")
  @CacheEvict(value = "TypesCache", allEntries = true)
  public ResponseEntity<?> deleteType(@PathVariable("productTypeCode") int productTypeCode) {
    try {
      String resultMessage = productTypeService.delete(productTypeCode);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchProductTypesByKeyword(@RequestParam("typeName") String typeName) {
    try {
      List<ProductType> searchResults = productTypeService.searchByKeyword(typeName);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @CacheEvict(value = "TypesCache", allEntries = true)
  public void invalidateTypesCache() {}
}
