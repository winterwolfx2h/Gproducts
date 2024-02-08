package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductTechnicalType;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ProductTechnicalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/productTechnicalType")
public class ProductTechnicalTypeController {

    @Autowired
    private ProductTechnicalTypeService productTechnicalTypeService;

    @PostMapping
    public ResponseEntity<ProductTechnicalType> createProductTechnicalType(@RequestBody ProductTechnicalType productTechnicalType) {
        try {
            ProductTechnicalType createdProductTechnicalType = productTechnicalTypeService.create(productTechnicalType);
            return ResponseEntity.ok(createdProductTechnicalType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductTechnicalType>> getAllProductTechnicalTypes() {
        try {
            List<ProductTechnicalType> productTechnicalTypes = productTechnicalTypeService.read();
            return ResponseEntity.ok(productTechnicalTypes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_ProdTechTypeCode}")
    public ResponseEntity<ProductTechnicalType> getProductTechnicalTypeById(@PathVariable("po_ProdTechTypeCode") int po_ProdTechTypeCode) {
        try {
            ProductTechnicalType productTechnicalType = productTechnicalTypeService.findById(po_ProdTechTypeCode);
            return ResponseEntity.ok(productTechnicalType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_ProdTechTypeCode}")
    public ResponseEntity<ProductTechnicalType> updateProductTechnicalType(
            @PathVariable("po_ProdTechTypeCode") int po_ProdTechTypeCode,
            @RequestBody ProductTechnicalType updatedProductTechnicalType) {

        try {
            ProductTechnicalType updatedProductTechnicalTypeResult = productTechnicalTypeService.update(po_ProdTechTypeCode, updatedProductTechnicalType);
            return ResponseEntity.ok(updatedProductTechnicalTypeResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{po_ProdTechTypeCode}")
    public ResponseEntity<String> deleteProductTechnicalType(@PathVariable("po_ProdTechTypeCode") int po_ProdTechTypeCode) {
        try {
            String resultMessage = productTechnicalTypeService.delete(po_ProdTechTypeCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductTechnicalType>> searchProductTechnicalTypesByKeyword(@RequestParam("name") String name) {
        try {
            List<ProductTechnicalType> searchResults = productTechnicalTypeService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
