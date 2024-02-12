package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelationship;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product-offering-relationship")
public class ProductOfferingRelationshipController {

    @Autowired
    private ProductOfferingRelationshipService productOfferingRelationshipService;

    @PostMapping
    public ResponseEntity<?> createProductOfferingRelationship(@RequestBody ProductOfferingRelationship productOfferingRelationship) {
        try {
            ProductOfferingRelationship createdProductOfferingRelationship = productOfferingRelationshipService.create(productOfferingRelationship);
            return ResponseEntity.ok(createdProductOfferingRelationship);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProductOfferingRelationships() {
        try {
            List<ProductOfferingRelationship> productOfferingRelationships = productOfferingRelationshipService.read();
            return ResponseEntity.ok(productOfferingRelationships);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{por_Code}")
    public ResponseEntity<?> getProductOfferingRelationshipById(@PathVariable("por_Code") int por_Code) {
        try {
            ProductOfferingRelationship productOfferingRelationship = productOfferingRelationshipService.findById(por_Code);
            return ResponseEntity.ok(productOfferingRelationship);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{por_Code}")
    public ResponseEntity<?> updateProductOfferingRelationship(
            @PathVariable("por_Code") int por_Code,
            @RequestBody ProductOfferingRelationship updatedProductOfferingRelationship) {
        try {
            ProductOfferingRelationship updatedProduct = productOfferingRelationshipService.update(por_Code, updatedProductOfferingRelationship);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{por_Code}")
    public ResponseEntity<?> deleteProductOfferingRelationship(@PathVariable("por_Code") int por_Code) {
        try {
            String resultMessage = productOfferingRelationshipService.delete(por_Code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
