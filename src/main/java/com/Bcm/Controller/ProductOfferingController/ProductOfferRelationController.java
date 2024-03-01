package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.SubClasses.RelationType;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Status;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.RelationTypeService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RelationTypeService relationTypeService;

    @Autowired
    private StatusService statusService;

    @PostMapping("/addProdOffRelation")
    public ResponseEntity<?> createProductOfferRelation(@RequestBody ProductOfferRelation productOfferRelation) {

        String typeName = productOfferRelation.getType().getName();
        String statusName = productOfferRelation.getStatus().getName();

        RelationType type = relationTypeService.findByName(typeName);
        Status status = statusService.findByName(statusName);

        if (type != null && status != null) {
            productOfferRelation.setType(type);
            productOfferRelation.setStatus(status);

            ProductOfferRelation createdProductSpecification = productOfferRelationService.create(productOfferRelation);
            return ResponseEntity.ok(createdProductSpecification);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (type == null) errorMessage.append(" Type with name: ").append(typeName);
            if (status == null) errorMessage.append(" Status with name: ").append(statusName);
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
    }

    @GetMapping
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
}
