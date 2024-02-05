package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelationship;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ProductOfferingRelationship> createProductOfferingRelationship(@RequestBody ProductOfferingRelationship productOfferingRelationship) {
        ProductOfferingRelationship createdProductOfferingRelationship = productOfferingRelationshipService.create(productOfferingRelationship);
        return ResponseEntity.ok(createdProductOfferingRelationship);
    }

    @GetMapping
    public ResponseEntity<List<ProductOfferingRelationship>> getAllProductOfferingRelationships() {
        List<ProductOfferingRelationship> productOfferingRelationships = productOfferingRelationshipService.read();
        return ResponseEntity.ok(productOfferingRelationships);
    }

    @GetMapping("/{por_Code}")
    public ResponseEntity<ProductOfferingRelationship> getProductOfferingRelationshipById(@PathVariable("por_Code") int por_Code) {
        ProductOfferingRelationship productOfferingRelationship = productOfferingRelationshipService.findById(por_Code);
        return ResponseEntity.ok(productOfferingRelationship);
    }

    @PutMapping("/{por_Code}")
    public ResponseEntity<ProductOfferingRelationship> updateProductOfferingRelationship(
            @PathVariable("por_Code") int por_Code,
            @RequestBody ProductOfferingRelationship updatedProductOfferingRelationship) {

        ProductOfferingRelationship updatedProduct = productOfferingRelationshipService.update(por_Code, updatedProductOfferingRelationship);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{por_Code}")
    public ResponseEntity<String> deleteProductOfferingRelationship(@PathVariable("por_Code") int por_Code) {
        String resultMessage = productOfferingRelationshipService.delete(por_Code);
        return ResponseEntity.ok(resultMessage);
    }
    
}
