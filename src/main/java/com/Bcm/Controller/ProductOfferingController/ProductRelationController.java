package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.ProductRelation;
import com.Bcm.Model.ProductOfferingABE.SubClasses.RelationType;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductRelationService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.RelationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product-relation")
public class ProductRelationController {

    final ProductRelationService productRelationService;
    final RelationTypeService relationTypeService;
    @PostMapping("/addProdOfferingRelation`")
    public ResponseEntity<?> createproductRelation(@RequestBody ProductRelation productRelation) {

        String RelationTypeName = productRelation.getType().getName();

        RelationType RelationType = relationTypeService.findByName(RelationTypeName);

        if (RelationType != null) {
            productRelation.setType(RelationType);

            ProductRelation createdproductRelation = productRelationService.create(productRelation);
            return ResponseEntity.ok(createdproductRelation);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (RelationType == null) errorMessage.append(" Family with name: ").append(RelationTypeName);
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
    }

    @GetMapping
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
    public ResponseEntity<?> updateproductRelation(
            @PathVariable("poRelation_Code") int poRelation_Code,
            @RequestBody ProductRelation updatedproductRelation) {
        try {
            ProductRelation updatedGroup = productRelationService.update(poRelation_Code, updatedproductRelation);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{poRelation_Code}")
    public ResponseEntity<?> deleteproductRelation(@PathVariable("poRelation_Code") int poRelation_Code) {
        try {
            String resultMessage = productRelationService.delete(poRelation_Code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

}

