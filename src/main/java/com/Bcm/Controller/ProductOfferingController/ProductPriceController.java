package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductPrice")
@RequiredArgsConstructor
public class ProductPriceController {


    final ProductPriceService productPriceService;

    @PostMapping("/addProductPrice")
    public ResponseEntity<?> createType(@RequestBody ProductPrice productPrice) {
        try {
            ProductPrice createdProductPrice = productPriceService.create(productPrice);
            return ResponseEntity.ok(createdProductPrice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/listProductPrice")
    public ResponseEntity<List<ProductPrice>> getAllProductPrices() {
        try {
            List<ProductPrice> ProductPrices = productPriceService.read();
            return ResponseEntity.ok(ProductPrices);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{productPriceCode}")
    public ResponseEntity<ProductPrice> getProductPriceById(@PathVariable("productPriceCode") int productPriceCode) {
        try {
            ProductPrice ProductPrice = productPriceService.findById(productPriceCode);
            return ResponseEntity.ok(ProductPrice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{productPriceCode}")
    public ResponseEntity<?> updateProductPrice(
            @PathVariable("productPriceCode") int productPriceCode,
            @RequestBody ProductPrice updatedProductPrice) {
        try {
            ProductPrice updatedGroup = productPriceService.update(productPriceCode, updatedProductPrice);
            return ResponseEntity.ok(updatedGroup);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @DeleteMapping("/{productPriceCode}")
    public ResponseEntity<String> deleteProductPrice(@PathVariable("productPriceCode") int productPriceCode) {
        try {
            String resultMessage = productPriceService.delete(productPriceCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductPrice>> searchProductPriceByPrice(@RequestParam("price") float price) {
        try {
            List<ProductPrice> searchResults = productPriceService.searchByPrice(price);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
