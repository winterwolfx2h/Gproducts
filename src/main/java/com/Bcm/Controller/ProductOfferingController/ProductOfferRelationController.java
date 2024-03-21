package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductOfferRelation")
public class ProductOfferRelationController {

    @Autowired
    private ProductOfferRelationService productOfferRelationService;


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

    @GetMapping("/{PoOfferRelation_Code}")
    public ResponseEntity<?> getProductOfferRelationById(@PathVariable("PoOfferRelation_Code") int PoOfferRelation_Code) {
        try {
            ProductOfferRelation productOfferRelation = productOfferRelationService.findById(PoOfferRelation_Code);
            return ResponseEntity.ok(productOfferRelation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{PoOfferRelation_Code}")
    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public ResponseEntity<?> updateProductOfferRelation(
            @PathVariable("PoOfferRelation_Code") int PoOfferRelation_Code,
            @RequestBody ProductOfferRelation updatedProductOfferRelation) {
        try {
            ProductOfferRelation updatedGroup = productOfferRelationService.update(PoOfferRelation_Code, updatedProductOfferRelation);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{PoOfferRelation_Code}")
    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public ResponseEntity<?> deleteProductOfferRelation(@PathVariable("PoOfferRelation_Code") int PoOfferRelation_Code) {
        try {
            String resultMessage = productOfferRelationService.delete(PoOfferRelation_Code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductOfferRelationsByKeyword(@RequestParam("name") String name) {
        try {
            List<ProductOfferRelation> searchResults = productOfferRelationService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public void invalidateProdOfferRelationCache() {
    }
}
