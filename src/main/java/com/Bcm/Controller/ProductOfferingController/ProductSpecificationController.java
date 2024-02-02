package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductSpecification")
public class ProductSpecificationController  {

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @PostMapping
    public ResponseEntity<ProductSpecification> createProductSpecification(@RequestBody ProductSpecification ProductSpecification) {
        ProductSpecification createdProductSpecification = productSpecificationService.create(ProductSpecification);
        return ResponseEntity.ok(createdProductSpecification);
    }

    @GetMapping
    public ResponseEntity<List<ProductSpecification>> getAllProductSpecifications() {
        List<ProductSpecification> ProductSpecifications = productSpecificationService.read();
        return ResponseEntity.ok(ProductSpecifications);
    }

    @GetMapping("/{po_SpecCode}")
    public ResponseEntity<ProductSpecification> getProductSpecificationById(@PathVariable("po_SpecCode") int po_SpecCode) {
        ProductSpecification ProductSpecification = productSpecificationService.findById(po_SpecCode);
        return ResponseEntity.ok(ProductSpecification);
    }

    @PutMapping("/{po_SpecCode}")
    public ResponseEntity<ProductSpecification> updateProductSpecification(
            @PathVariable("po_SpecCode") int po_SpecCode,
            @RequestBody ProductSpecification updatedProductSpecification) {

        ProductSpecification updatedGroup = productSpecificationService.update(po_SpecCode, updatedProductSpecification);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_SpecCode}")
    public ResponseEntity<String> deleteProductSpecification(@PathVariable("po_SpecCode") int po_SpecCode) {
        String resultMessage = productSpecificationService.delete(po_SpecCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductSpecification>> searchProductSpecificationsByKeyword(@RequestParam("name") String name) {
        List<ProductSpecification> searchResults = productSpecificationService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
