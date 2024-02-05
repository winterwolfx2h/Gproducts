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
    public ResponseEntity<ProductSubType> createProductSubType(@RequestBody ProductSubType ProductSubType) {
        ProductSubType createdProductSubType = productSubTypeService.create(ProductSubType);
        return ResponseEntity.ok(createdProductSubType);
    }

    @GetMapping
    public ResponseEntity<List<ProductSubType>> getAllProductSubTypes() {
        List<ProductSubType> ProductSubTypes = productSubTypeService.read();
        return ResponseEntity.ok(ProductSubTypes);
    }

    @GetMapping("/{po_ProdSubTypeCode}")
    public ResponseEntity<ProductSubType> getProductSubTypeById(@PathVariable("po_ProdSubTypeCode") int po_ProdSubTypeCode) {
        ProductSubType ProductSubType = productSubTypeService.findById(po_ProdSubTypeCode);
        return ResponseEntity.ok(ProductSubType);
    }

    @PutMapping("/{po_ProdSubTypeCode}")
    public ResponseEntity<ProductSubType> updateProductSubType(
            @PathVariable("po_ProdSubTypeCode") int po_ProdSubTypeCode,
            @RequestBody ProductSubType updatedProductSubType) {

        ProductSubType updatedGroup = productSubTypeService.update(po_ProdSubTypeCode, updatedProductSubType);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_ProdSubTypeCode}")
    public ResponseEntity<String> deleteProductSubType(@PathVariable("po_ProdSubTypeCode") int po_ProdSubTypeCode) {
        String resultMessage = productSubTypeService.delete(po_ProdSubTypeCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductSubType>> searchProductSubTypesByKeyword(@RequestParam("name") String name) {
        List<ProductSubType> searchResults = productSubTypeService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
