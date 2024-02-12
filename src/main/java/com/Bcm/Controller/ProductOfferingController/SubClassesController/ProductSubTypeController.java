package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductSubType;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ProductSubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/productSubType")
public class ProductSubTypeController {

    @Autowired
    private ProductSubTypeService productSubTypeService;

    @PostMapping
    public ResponseEntity<ProductSubType> createProductSubType(@RequestBody ProductSubType productSubType) {
        try {
            ProductSubType createdProductSubType = productSubTypeService.create(productSubType);
            return ResponseEntity.ok(createdProductSubType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductSubType>> getAllProductSubTypes() {
        try {
            List<ProductSubType> productSubTypes = productSubTypeService.read();
            return ResponseEntity.ok(productSubTypes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_ProdSubTypeCode}")
    public ResponseEntity<ProductSubType> getProductSubTypeById(@PathVariable("po_ProdSubTypeCode") int po_ProdSubTypeCode) {
        try {
            ProductSubType productSubType = productSubTypeService.findById(po_ProdSubTypeCode);
            return ResponseEntity.ok(productSubType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_ProdSubTypeCode}")
    public ResponseEntity<ProductSubType> updateProductSubType(
            @PathVariable("po_ProdSubTypeCode") int po_ProdSubTypeCode,
            @RequestBody ProductSubType updatedProductSubType) {

        try {
            ProductSubType updatedProductSubTypeResult = productSubTypeService.update(po_ProdSubTypeCode, updatedProductSubType);
            return ResponseEntity.ok(updatedProductSubTypeResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{po_ProdSubTypeCode}")
    public ResponseEntity<String> deleteProductSubType(@PathVariable("po_ProdSubTypeCode") int po_ProdSubTypeCode) {
        try {
            String resultMessage = productSubTypeService.delete(po_ProdSubTypeCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductSubType>> searchProductSubTypesByKeyword(@RequestParam("name") String name) {
        try {
            List<ProductSubType> searchResults = productSubTypeService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
