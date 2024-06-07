package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ProductPriceGroupAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductPriceGroup;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductPriceGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductPriceGroup")
@RequiredArgsConstructor
public class ProductPriceGroupController {


    final ProductPriceGroupService productPriceGroupService;

    @PostMapping("/addProductPriceGroup")
    public ResponseEntity<?> createType(@RequestBody ProductPriceGroup productPriceGroup) {
        try {
            ProductPriceGroup createdProductPriceGroup = productPriceGroupService.create(productPriceGroup);
            return ResponseEntity.ok(createdProductPriceGroup);
        } catch (ProductPriceGroupAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


    @GetMapping("/listProductPriceGroup")
    public ResponseEntity<List<ProductPriceGroup>> getAllProductPriceGroups() {
        try {
            List<ProductPriceGroup> ProductPriceGroups = productPriceGroupService.read();
            return ResponseEntity.ok(ProductPriceGroups);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{productPriceGroupCode}")
    public ResponseEntity<ProductPriceGroup> getProductPriceGroupById(@PathVariable("productPriceGroupCode") int productPriceGroupCode) {
        try {
            ProductPriceGroup ProductPriceGroup = productPriceGroupService.findById(productPriceGroupCode);
            return ResponseEntity.ok(ProductPriceGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{productPriceGroupCode}")
    public ResponseEntity<?> updateProductPriceGroup(
            @PathVariable("productPriceGroupCode") int productPriceGroupCode,
            @RequestBody ProductPriceGroup updatedProductPriceGroup) {
        try {
            ProductPriceGroup updatedGroup = productPriceGroupService.update(productPriceGroupCode, updatedProductPriceGroup);
            return ResponseEntity.ok(updatedGroup);
        } catch (ProductPriceGroupAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @DeleteMapping("/{productPriceGroupCode}")
    public ResponseEntity<String> deleteProductPriceGroup(@PathVariable("productPriceGroupCode") int productPriceGroupCode) {
        try {
            String resultMessage = productPriceGroupService.delete(productPriceGroupCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductPriceGroup>> searchProductPriceGroupsByKeyword(@RequestParam("name") String name) {
        try {
            List<ProductPriceGroup> searchResults = productPriceGroupService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
