package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.PrimeryKeyProductRelation;
import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductOfferRelation")
@RequiredArgsConstructor
public class ProductOfferRelationController {

    final ProductOfferRelationService productOfferRelationService;

    @PostMapping("/addProdOffRelations")
    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public ResponseEntity<List<ProductOfferRelation>> createProductOfferRelations(@RequestBody List<ProductOfferRelation> productOfferRelations) {
        List<ProductOfferRelation> createdProductOfferRelations = new ArrayList<>();
        for (ProductOfferRelation productOfferRelation : productOfferRelations) {
            ProductOfferRelation newProductOfferRelation = new ProductOfferRelation();
            newProductOfferRelation.setId(new PrimeryKeyProductRelation(productOfferRelation.getId().getRelatedProductId()));
            newProductOfferRelation.setType(productOfferRelation.getType());
            newProductOfferRelation.setProduct_id(productOfferRelation.getProduct_id());
            createdProductOfferRelations.addAll(productOfferRelationService.create(Collections.singletonList(newProductOfferRelation)));
        }
        return ResponseEntity.ok(createdProductOfferRelations);
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

    @GetMapping("/search")
    public ResponseEntity<?> searchProductOfferRelationsByKeyword(@RequestParam("type") String type) {
        try {
            List<ProductOfferRelation> searchResults = productOfferRelationService.searchByKeyword(type);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findProductOfferRelationById(@RequestParam("id") PrimeryKeyProductRelation id) {
        try {
            ProductOfferRelation productOfferRelation = productOfferRelationService.findById(id);
            return ResponseEntity.ok(productOfferRelation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/deleteById")
    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public ResponseEntity<?> deleteProductOfferRelationById(@RequestParam("id") PrimeryKeyProductRelation id) {
        try {
            productOfferRelationService.deleteById(id);
            return ResponseEntity.ok("ProductOfferRelation deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/update")
    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public ResponseEntity<?> updateProductOfferRelation(@RequestBody ProductOfferRelation productOfferRelation) {
        try {
            ProductOfferRelation updatedProductOfferRelation = productOfferRelationService.update(productOfferRelation);
            return ResponseEntity.ok(updatedProductOfferRelation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public void invalidateProdOfferRelationCache() {
    }
}
