package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.BundledProductOfferOption;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BundledProductOfferOptionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<BundledProductOfferOption> createBundledProductOfferOption(@RequestBody BundledProductOfferOption bundledProductOfferOption) {
        BundledProductOfferOption createdBundledProductOfferOption = bundledProductOfferOptionService.create(bundledProductOfferOption);
        return ResponseEntity.ok(createdBundledProductOfferOption);
    }

    @GetMapping
    public ResponseEntity<List<BundledProductOfferOption>> getAllBundledProductOfferOptions() {
        List<BundledProductOfferOption> bundledProductOfferOptions = bundledProductOfferOptionService.read();
        return ResponseEntity.ok(bundledProductOfferOptions);
    }

    @GetMapping("/{bdoo_Code}")
    public ResponseEntity<BundledProductOfferOption> getBundledProductOfferOptionById(@PathVariable("bdoo_Code") int bdoo_Code) {
        BundledProductOfferOption bundledProductOfferOption = bundledProductOfferOptionService.findById(bdoo_Code);
        return ResponseEntity.ok(bundledProductOfferOption);
    }

    @PutMapping("/{bdoo_Code}")
    public ResponseEntity<BundledProductOfferOption> updateBundledProductOfferOption(
            @PathVariable("bdoo_Code") int bdoo_Code,
            @RequestBody BundledProductOfferOption updatedBundledProductOfferOption) {

        BundledProductOfferOption updatedProduct = bundledProductOfferOptionService.update(bdoo_Code, updatedBundledProductOfferOption);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{bdoo_Code}")
    public ResponseEntity<String> deleteBundledProductOfferOption(@PathVariable("bdoo_Code") int bdoo_Code) {
        String resultMessage = bundledProductOfferOptionService.delete(bdoo_Code);
        return ResponseEntity.ok(resultMessage);
    }


}
