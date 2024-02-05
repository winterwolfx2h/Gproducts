package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.BundledProductOffer;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BundledProductOfferService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<BundledProductOffer> createBundledProductOffer(@RequestBody BundledProductOffer bundledProductOffer) {
        BundledProductOffer createdBundledProductOffer = bundledProductOfferService.create(bundledProductOffer);
        return ResponseEntity.ok(createdBundledProductOffer);
    }

    @GetMapping
    public ResponseEntity<List<BundledProductOffer>> getAllBundledProductOffers() {
        List<BundledProductOffer> bundledProductOffers = bundledProductOfferService.read();
        return ResponseEntity.ok(bundledProductOffers);
    }

    @GetMapping("/{bdo_Code}")
    public ResponseEntity<BundledProductOffer> getBundledProductOfferById(@PathVariable("bdo_Code") int bdo_Code) {
        BundledProductOffer bundledProductOffer = bundledProductOfferService.findById(bdo_Code);
        return ResponseEntity.ok(bundledProductOffer);
    }

    @PutMapping("/{bdo_Code}")
    public ResponseEntity<BundledProductOffer> updateBundledProductOffer(
            @PathVariable("bdo_Code") int bdo_Code,
            @RequestBody BundledProductOffer updatedBundledProductOffer) {

        BundledProductOffer updatedProduct = bundledProductOfferService.update(bdo_Code, updatedBundledProductOffer);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{bdo_Code}")
    public ResponseEntity<String> deleteBundledProductOffer(@PathVariable("bdo_Code") int bdo_Code) {
        String resultMessage = bundledProductOfferService.delete(bdo_Code);
        return ResponseEntity.ok(resultMessage);
    }
}
