package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelation;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Type;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingRelationService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.StatusService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product-offering-relation")
public class ProductOfferingRelationController {

    final ProductOfferingRelationService productOfferingRelationService;
    final TypeService typeService;
    @PostMapping("/addProdOfferingRelation")
    public ResponseEntity<?> createProductOfferingRelation(@RequestBody ProductOfferingRelation productOfferingRelation) {

        String typeName = productOfferingRelation.getType().getName();

        Type type = typeService.findByName(typeName);

        if (type != null) {
            productOfferingRelation.setType(type);

            ProductOfferingRelation createdProductOfferingRelation = productOfferingRelationService.create(productOfferingRelation);
            return ResponseEntity.ok(createdProductOfferingRelation);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (type == null) errorMessage.append(" Family with name: ").append(typeName);
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProductOfferingRelations() {
        try {
            List<ProductOfferingRelation> ProductOfferingRelations = productOfferingRelationService.read();
            return ResponseEntity.ok(ProductOfferingRelations);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{poRelation_Code}")
    public ResponseEntity<?> getProductOfferingRelationById(@PathVariable("poRelation_Code") int poRelation_Code) {
        try {
            ProductOfferingRelation ProductOfferingRelation = productOfferingRelationService.findById(poRelation_Code);
            return ResponseEntity.ok(ProductOfferingRelation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{poRelation_Code}")
    public ResponseEntity<?> updateProductOfferingRelation(
            @PathVariable("poRelation_Code") int poRelation_Code,
            @RequestBody ProductOfferingRelation updatedProductOfferingRelation) {
        try {
            ProductOfferingRelation updatedGroup = productOfferingRelationService.update(poRelation_Code, updatedProductOfferingRelation);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{poRelation_Code}")
    public ResponseEntity<?> deleteProductOfferingRelation(@PathVariable("poRelation_Code") int poRelation_Code) {
        try {
            String resultMessage = productOfferingRelationService.delete(poRelation_Code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchProductOfferingRelationsByKeyword(@RequestParam("name") String name) {
        try {
            List<ProductOfferingRelation> searchResults = productOfferingRelationService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}

