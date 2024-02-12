package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.BundledProductOfferOption;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BundledProductOfferOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/bundled-product-offer-option")
public class BundledProductOfferOptionController {

    @Autowired
    private BundledProductOfferOptionService bundledProductOfferOptionService;

    @PostMapping
    public ResponseEntity<?> createBundledProductOfferOption(@RequestBody BundledProductOfferOption bundledProductOfferOption) {
        try {
            BundledProductOfferOption createdBundledProductOfferOption = bundledProductOfferOptionService.create(bundledProductOfferOption);
            return ResponseEntity.ok(createdBundledProductOfferOption);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBundledProductOfferOptions() {
        try {
            List<BundledProductOfferOption> bundledProductOfferOptions = bundledProductOfferOptionService.read();
            return ResponseEntity.ok(bundledProductOfferOptions);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{bdoo_Code}")
    public ResponseEntity<?> getBundledProductOfferOptionById(@PathVariable("bdoo_Code") int bdoo_Code) {
        try {
            BundledProductOfferOption bundledProductOfferOption = bundledProductOfferOptionService.findById(bdoo_Code);
            return ResponseEntity.ok(bundledProductOfferOption);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{bdoo_Code}")
    public ResponseEntity<?> updateBundledProductOfferOption(
            @PathVariable("bdoo_Code") int bdoo_Code,
            @RequestBody BundledProductOfferOption updatedBundledProductOfferOption) {
        try {
            BundledProductOfferOption updatedProduct = bundledProductOfferOptionService.update(bdoo_Code, updatedBundledProductOfferOption);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{bdoo_Code}")
    public ResponseEntity<?> deleteBundledProductOfferOption(@PathVariable("bdoo_Code") int bdoo_Code) {
        try {
            String resultMessage = bundledProductOfferOptionService.delete(bdoo_Code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
