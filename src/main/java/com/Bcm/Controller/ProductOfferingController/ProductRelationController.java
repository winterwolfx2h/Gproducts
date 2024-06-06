package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ProductOfferingABE.ProductRelation;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product-relation")
public class ProductRelationController {

    final ProductRelationService productRelationService;

    @PostMapping("/addProdRelation")
    @CacheEvict(value = "ProdRelationCache", allEntries = true)
    public ResponseEntity<ProductRelation> createproductRelation(@RequestBody ProductRelation productRelation) {
        ProductRelation createdProductRelation = productRelationService.create(productRelation);
        return ResponseEntity.ok(createdProductRelation);
    }

    @GetMapping("/listProdRelations")
    @Cacheable(value = "ProdRelationCache")
    public ResponseEntity<?> getAllproductRelations() {
        try {
            List<ProductRelation> productRelations = productRelationService.read();
            return ResponseEntity.ok(productRelations);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{poRelation_Code}")
    public ResponseEntity<?> getproductRelationById(@PathVariable("poRelation_Code") int poRelation_Code) {
        try {
            ProductRelation productRelation = productRelationService.findById(poRelation_Code);
            return ResponseEntity.ok(productRelation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{poRelation_Code}")
    @CacheEvict(value = "ProdRelationCache", allEntries = true)
    public ResponseEntity<?> updateproductRelation(
            @PathVariable("poRelation_Code") int poRelation_Code,
            @RequestBody   ProductRelation updatedproductRelation) {
        try {
            ProductRelation updatedGroup = productRelationService.update(poRelation_Code, updatedproductRelation);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{poRelation_Code}")
    @CacheEvict(value = "ProdRelationCache", allEntries = true)
    public ResponseEntity<?> deleteproductRelation(@PathVariable("poRelation_Code") int poRelation_Code) {
        try {
            String resultMessage = productRelationService.delete(poRelation_Code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductRelation>> searchProductRelationByKeyword(@RequestParam("type") String type) {
        List<ProductRelation> searchResults = productRelationService.searchByKeyword(type);
        return ResponseEntity.ok(searchResults);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CacheEvict(value = "ProdRelationCache", allEntries = true)
    public void invalidateProdRelationCache() {
    }
}

