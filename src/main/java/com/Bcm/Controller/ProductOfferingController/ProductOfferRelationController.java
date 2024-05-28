package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductOfferRelation")
@RequiredArgsConstructor
public class ProductOfferRelationController {


    final ProductOfferRelationService productOfferRelationService;


    @PostMapping("/addProdOffRelation")
    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public ResponseEntity<ProductOfferRelation> createProductOfferRelation(@RequestBody ProductOfferRelation productOfferRelation) {
        ProductOfferRelation createdProductOfferRelation = productOfferRelationService.create(productOfferRelation);
        return ResponseEntity.ok(createdProductOfferRelation);
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

    @GetMapping("/{pOfferRelationCode}")
    public ResponseEntity<?> getProductOfferRelationById(@PathVariable("pOfferRelationCode") int pOfferRelationCode) {
        try {
            ProductOfferRelation productOfferRelation = productOfferRelationService.findById(pOfferRelationCode);
            return ResponseEntity.ok(productOfferRelation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{pOfferRelationCode}")
    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public ResponseEntity<?> updateProductOfferRelation(
            @PathVariable("pOfferRelationCode") int pOfferRelationCode,
            @RequestBody ProductOfferRelation updatedProductOfferRelation) {
        try {
            ProductOfferRelation updatedGroup = productOfferRelationService.update(pOfferRelationCode, updatedProductOfferRelation);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{pOfferRelationCode}")
    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public ResponseEntity<?> deleteProductOfferRelation(@PathVariable("pOfferRelationCode") int pOfferRelationCode) {
        try {
            String resultMessage = productOfferRelationService.delete(pOfferRelationCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductOfferRelationsByKeyword(@RequestParam("type") String type) {
        try {
            List<ProductOfferRelation> searchResults = productOfferRelationService.searchByKeyword(type);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public void invalidateProdOfferRelationCache() {
    }
}
