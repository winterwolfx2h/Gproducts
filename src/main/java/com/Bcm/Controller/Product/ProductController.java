package com.Bcm.Controller.Product;

import com.Bcm.Model.product.Product;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/Product")
public class ProductController {


    final ProductService productService;
    final ProductOfferingService productOfferingService;

    @GetMapping("/ProductList")
    public ResponseEntity<?> getAllProduct() {
        try {
            List<Product> product = productService.read();
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

   /* @GetMapping("/productsWithPOBasicParent")
    public ResponseEntity<List<Product>> getProductsByPOBasicParent() {
        String parentName = "PO_Basic";
        List<ProductOffering> productOfferings = productOfferingService.findByParentName(parentName);
        List<Product> products = productOfferings.stream()
                .map(ProductOffering::convertToProduct)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }*/
}

