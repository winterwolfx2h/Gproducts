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
    public ResponseEntity<ProductTechnicalType> createProductTechnicalType(@RequestBody ProductTechnicalType ProductTechnicalType) {
        ProductTechnicalType createdProductTechnicalType = productTechnicalTypeService.create(ProductTechnicalType);
        return ResponseEntity.ok(createdProductTechnicalType);
    }

    @GetMapping
    public ResponseEntity<List<ProductTechnicalType>> getAllProductTechnicalTypes() {
        List<ProductTechnicalType> ProductTechnicalTypes = productTechnicalTypeService.read();
        return ResponseEntity.ok(ProductTechnicalTypes);
    }

    @GetMapping("/{po_ProdTechTypeCode}")
    public ResponseEntity<ProductTechnicalType> getProductTechnicalTypeById(@PathVariable("po_ProdTechTypeCode") int po_ProdTechTypeCode) {
        ProductTechnicalType ProductTechnicalType = productTechnicalTypeService.findById(po_ProdTechTypeCode);
        return ResponseEntity.ok(ProductTechnicalType);
    }

    @PutMapping("/{po_ProdTechTypeCode}")
    public ResponseEntity<ProductTechnicalType> updateProductTechnicalType(
            @PathVariable("po_ProdTechTypeCode") int po_ProdTechTypeCode,
            @RequestBody ProductTechnicalType updatedProductTechnicalType) {

        ProductTechnicalType updatedGroup = productTechnicalTypeService.update(po_ProdTechTypeCode, updatedProductTechnicalType);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_ProdTechTypeCode}")
    public ResponseEntity<String> deleteProductTechnicalType(@PathVariable("po_ProdTechTypeCode") int po_ProdTechTypeCode) {
        String resultMessage = productTechnicalTypeService.delete(po_ProdTechTypeCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductTechnicalType>> searchProductTechnicalTypesByKeyword(@RequestParam("name") String name) {
        List<ProductTechnicalType> searchResults = productTechnicalTypeService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
