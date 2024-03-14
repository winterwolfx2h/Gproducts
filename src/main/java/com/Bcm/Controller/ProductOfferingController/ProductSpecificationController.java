package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import com.Bcm.Service.Srvc.POPlanService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductSpecification")
public class ProductSpecificationController {

    final private ProductSpecificationService productSpecificationService;
    final private POPlanService poPlanService;

    @PostMapping("/addProdSpec")
    public ResponseEntity<?> createProductSpecification(@RequestBody ProductSpecification ProductSpecification) {

        String poPlanName = ProductSpecification.getPoPlan().getSHDES();
        POPlan poPlan = poPlanService.findBySHDES(poPlanName);

        if (poPlan != null) {
            ProductSpecification.setPoPlan(poPlan);

            ProductSpecification createdProductSpecification = productSpecificationService.create(ProductSpecification);
            return ResponseEntity.ok(createdProductSpecification);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (poPlan == null) errorMessage.append(" POPPLAN with name: ").append(poPlanName);
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
    }

    @GetMapping("/listProdSpec")
    public ResponseEntity<?> getAllProductSpecifications() {
        try {
            List<ProductSpecification> ProductSpecifications = productSpecificationService.read();
            return ResponseEntity.ok(ProductSpecifications);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{po_SpecCode}")
    public ResponseEntity<?> getProductSpecificationById(@PathVariable("po_SpecCode") int po_SpecCode) {
        try {
            ProductSpecification ProductSpecification = productSpecificationService.findById(po_SpecCode);
            return ResponseEntity.ok(ProductSpecification);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/updatePOPlan/{po_SpecCode}")
    public ResponseEntity<?> update(
            @PathVariable int po_SpecCode,
            @RequestBody ProductSpecification productSpecification) {

        try {
            ProductSpecification existingProductSpecification = productSpecificationService.findById(po_SpecCode);
            if (existingProductSpecification == null) {
                return ResponseEntity.notFound().build();
            }
            String poPlanName = productSpecification.getPoPlan().getSHDES();
            POPlan existingPoPlan = poPlanService.findBySHDES(poPlanName);

            if (existingPoPlan == null) {
                return ResponseEntity.badRequest().body("Poplan not found.");
            }
            existingProductSpecification.setCategory(productSpecification.getCategory());
            existingProductSpecification.setPoPlan(existingPoPlan);
            existingProductSpecification.setExternalId(productSpecification.getExternalId());


            ProductSpecification updatedProductSpecification = productSpecificationService.update(po_SpecCode, existingProductSpecification);
            return ResponseEntity.ok(updatedProductSpecification);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{po_SpecCode}")
    public ResponseEntity<?> deleteProductSpecification(@PathVariable("po_SpecCode") int po_SpecCode) {
        try {
            String resultMessage = productSpecificationService.delete(po_SpecCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


}
