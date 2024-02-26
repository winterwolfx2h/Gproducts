package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import com.Bcm.Service.Srvc.POPlanService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductSpecificationService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.RatePlanService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductSpecification")
public class ProductSpecificationController {

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @Autowired
    private FamilyService familyService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private SubMarketService subMarketService;
    @Autowired
    private RatePlanService ratePlanService;
    @Autowired
    private POPlanService poPlanService;

    @PostMapping
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

    @GetMapping
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

    @PutMapping("/{po_SpecCode}")
    public ResponseEntity<?> updateProductSpecification(
            @PathVariable("po_SpecCode") int po_SpecCode,
            @RequestBody ProductSpecification updatedProductSpecification) {
        try {
            ProductSpecification updatedGroup = productSpecificationService.update(po_SpecCode, updatedProductSpecification);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
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

    @GetMapping("/search")
    public ResponseEntity<?> searchProductSpecificationsByKeyword(@RequestParam("name") String name) {
        try {
            List<ProductSpecification> searchResults = productSpecificationService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
