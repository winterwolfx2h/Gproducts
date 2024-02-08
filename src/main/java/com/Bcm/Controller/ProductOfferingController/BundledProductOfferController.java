package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.BundledProductOffer;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BundledProductOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/bundled-product-offer")
public class BundledProductOfferController {

    @Autowired
    private BundledProductOfferService bundledProductOfferService;

    @PostMapping
    public ResponseEntity<?> createBundledProductOffer(@RequestBody BundledProductOffer bundledProductOffer) {
        try {
            BundledProductOffer createdBundledProductOffer = bundledProductOfferService.create(bundledProductOffer);
            return ResponseEntity.ok(createdBundledProductOffer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBundledProductOffers() {
        try {
            List<BundledProductOffer> bundledProductOffers = bundledProductOfferService.read();
            return ResponseEntity.ok(bundledProductOffers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{bdo_Code}")
    public ResponseEntity<?> getBundledProductOfferById(@PathVariable("bdo_Code") int bdo_Code) {
        try {
            BundledProductOffer bundledProductOffer = bundledProductOfferService.findById(bdo_Code);
            return ResponseEntity.ok(bundledProductOffer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{bdo_Code}")
    public ResponseEntity<?> updateBundledProductOffer(
            @PathVariable("bdo_Code") int bdo_Code,
            @RequestBody BundledProductOffer updatedBundledProductOffer) {
        try {
            BundledProductOffer updatedProduct = bundledProductOfferService.update(bdo_Code, updatedBundledProductOffer);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{bdo_Code}")
    public ResponseEntity<?> deleteBundledProductOffer(@PathVariable("bdo_Code") int bdo_Code) {
        try {
            String resultMessage = bundledProductOfferService.delete(bdo_Code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
