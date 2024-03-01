package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.ProductResource;
import com.Bcm.Model.ServiceABE.ServiceSpecConfig;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductResourceService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceSpecConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductResource")
public class ProductResourceController {

    final ProductResourceService productResourceService;
    final ServiceSpecConfigService serviceSpecConfigService;

    @PostMapping
    public ResponseEntity<ProductResource> createType(@RequestBody ProductResource productResource,
                                                      @RequestParam("lrSSC_code") int lrSSC_code,
                                                      @RequestParam("prSSC_code") int prSSC_code) {
        try {
            ServiceSpecConfig lrServiceSpecConfig = serviceSpecConfigService.findById(lrSSC_code);
            ServiceSpecConfig prServiceSpecConfig = serviceSpecConfigService.findById(prSSC_code);

            productResource.setLrServiceId(String.valueOf(lrSSC_code));
            productResource.setPrServiceId(String.valueOf(prSSC_code));

            ProductResource createdProductResource = productResourceService.create(productResource);
            return ResponseEntity.ok(createdProductResource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProductResources() {
        try {
            List<ProductResource> ProductResources = productResourceService.read();
            return ResponseEntity.ok(ProductResources);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{PrResId}")
    public ResponseEntity<?> getProductResourceById(@PathVariable("PrResId") int PrResId) {
        try {
            ProductResource ProductResource = productResourceService.findById(PrResId);
            return ResponseEntity.ok(ProductResource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{PrResId}")
    public ResponseEntity<?> updateProductResource(
            @PathVariable("PrResId") int PrResId,
            @RequestBody ProductResource updatedProductResource) {
        try {
            ProductResource updatedGroup = productResourceService.update(PrResId, updatedProductResource);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{PrResId}")
    public ResponseEntity<?> deleteProductResource(@PathVariable("PrResId") int PrResId) {
        try {
            String resultMessage = productResourceService.delete(PrResId);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductResourcesByKeyword(@RequestParam("name") String name) {
        try {
            List<ProductResource> searchResults = productResourceService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
