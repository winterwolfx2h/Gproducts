package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product-offerings")
public class ProductOfferingController {

    @Autowired
    private ProductOfferingService productOfferingService;

    @PostMapping
    public ResponseEntity<ProductOffering> createProductOffering(@RequestBody ProductOffering productOffering) {
        ProductOffering createdProductOffering = productOfferingService.create(productOffering);
        return ResponseEntity.ok(createdProductOffering);
    }

    @GetMapping
    public ResponseEntity<List<ProductOffering>> getAllProductOfferings() {
        List<ProductOffering> productOfferings = productOfferingService.read();
        return ResponseEntity.ok(productOfferings);
    }

    @GetMapping("/{po_code}")
    public ResponseEntity<ProductOffering> getProductOfferingById(@PathVariable("po_code") int po_code) {
        ProductOffering productOffering = productOfferingService.findById(po_code);
        return ResponseEntity.ok(productOffering);
    }

    @PutMapping("/{po_code}")
    public ResponseEntity<ProductOffering> updateProductOffering(
            @PathVariable("po_code") int po_code,
            @RequestBody ProductOffering updatedProductOffering) {

        ProductOffering updatedProduct = productOfferingService.update(po_code, updatedProductOffering);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{po_code}")
    public ResponseEntity<String> deleteProductOffering(@PathVariable("po_code") int po_code) {
        String resultMessage = productOfferingService.delete(po_code);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductOffering>> searchProductOfferingsByKeyword(@RequestParam("name") String name) {
        List<ProductOffering> searchResults = productOfferingService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
