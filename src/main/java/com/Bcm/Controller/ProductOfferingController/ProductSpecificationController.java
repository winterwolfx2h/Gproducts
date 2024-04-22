package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import com.Bcm.Service.Srvc.POPlanService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductSpecification")
public class ProductSpecificationController {

    final private ProductSpecificationService productSpecificationService;
    final private POPlanService poPlanService;

    @PostMapping("/addProdSpec")
    @CacheEvict(value = "ProdSpecCache", allEntries = true)
    public ResponseEntity<?> createProductSpecification(@RequestBody ProductSpecification productSpecification) {

        List<String> poPlanSHDES = productSpecification.getPoPlanSHDES();

        List<POPlan> existingPOPlans = new ArrayList<>();
        List<String> nonExistingShdes = new ArrayList<>();

        for (String shdes : poPlanSHDES) {
            POPlan poPlan = poPlanService.findBySHDES(shdes);
            if (poPlan != null) {
                existingPOPlans.add(poPlan);
            } else {
                nonExistingShdes.add(shdes);
            }
        }

        if (!nonExistingShdes.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("The following POPlans do not exist:");
            for (String shdes : nonExistingShdes) {
                errorMessage.append(" SHDES: ").append(shdes);
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        if (!existingPOPlans.isEmpty()) {
            List<String> existingShdes = new ArrayList<>();
            for (POPlan poPlan : existingPOPlans) {
                existingShdes.add(poPlan.getSHDES());
            }
            productSpecification.setPoPlanSHDES(existingShdes);
        }

        ProductSpecification createdProductSpecification = productSpecificationService.create(productSpecification);
        return ResponseEntity.ok(createdProductSpecification);
    }


    @GetMapping("/listProdSpec")
    @Cacheable(value = "ProdSpecCache")
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
    @CacheEvict(value = "ProdSpecCache", allEntries = true)
    public ResponseEntity<?> update(
            @PathVariable int po_SpecCode,
            @RequestBody ProductSpecification productSpecification) {

        try {
            ProductSpecification existingProductSpecification = productSpecificationService.findById(po_SpecCode);
            if (existingProductSpecification == null) {
                return ResponseEntity.notFound().build();
            }
            String poPlanName = productSpecification.getPoPlanSHDES().toString();
            POPlan existingPoPlan = poPlanService.findBySHDES(poPlanName);

            if (existingPoPlan == null) {
                return ResponseEntity.badRequest().body("Poplan not found.");
            }
            existingProductSpecification.setCategory(productSpecification.getCategory());
            existingProductSpecification.getPoPlanSHDES();
            existingProductSpecification.setBS_externalId(productSpecification.getBS_externalId());
            existingProductSpecification.setCS_externalId(productSpecification.getCS_externalId());


            ProductSpecification updatedProductSpecification = productSpecificationService.update(po_SpecCode, existingProductSpecification);
            return ResponseEntity.ok(updatedProductSpecification);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{po_SpecCode}")
    @CacheEvict(value = "ProdSpecCache", allEntries = true)
    public ResponseEntity<?> deleteProductSpecification(@PathVariable("po_SpecCode") int po_SpecCode) {
        try {
            String resultMessage = productSpecificationService.delete(po_SpecCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @CacheEvict(value = "ProdSpecCache", allEntries = true)
    public void invalidateProdSpecCache() {
    }

}
