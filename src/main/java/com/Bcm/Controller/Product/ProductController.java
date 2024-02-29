package com.Bcm.Controller.Product;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.product.Product;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/Product")
public class ProductController {


    final ProductService productService;
    final ProductOfferingService productOfferingService;

    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        try {
            List<Product> product = productService.read();
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/searchByFamily")
    public ResponseEntity<List<Product>> searchPfByFamily(@RequestParam("name") String name) {
        List<Product> searchResults = productService.searchWithFamily(name);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/productsWithPOBasicParent")
    public ResponseEntity<List<Product>> getProductsByPOBasicParent() {
        String parentName = "PO_Basic";
        List<ProductOffering> productOfferings = productOfferingService.findByParentName(parentName);
        List<Product> products = productOfferings.stream()
                .map(ProductOffering::convertToProduct)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
}

