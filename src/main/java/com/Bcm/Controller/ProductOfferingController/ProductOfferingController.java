package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Parent;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductSpecificationService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.CategoryService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ParentService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product-offerings")
public class ProductOfferingController {

    final  ProductOfferingService productOfferingService;
    final  CategoryService categoryService;
    final  ProductSpecificationService productSpecificationService;
    final  ParentService parentService;
    final  FamilyService familyService;
    final  TypeService typeService;


    @PostMapping("/addProdOff")
    public ResponseEntity<?> create(@RequestBody ProductOffering productOffering) {
        String productSpecName = productOffering.getProductSpecification().getName();
        String parentName = productOffering.getParent().getName();

        ProductSpecification productSpec = productSpecificationService.findByName(productSpecName);
        Parent parent = parentService.findByName(parentName);

        if (productSpec != null && parent != null) {
            productOffering.setProductSpecification(productSpec);
            productOffering.setParent(parent);

            ProductOffering createdProduct = productOfferingService.create(productOffering);
            return ResponseEntity.ok(createdProduct);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (productSpec == null) errorMessage.append(" ProductSpecification with name: ").append(productSpecName);
            if (parent == null) errorMessage.append(" Parent with name: ").append(parentName);
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductOffering>> getAllProductOfferings() {
        List<ProductOffering> productOfferings = productOfferingService.read();
        return ResponseEntity.ok(productOfferings);
    }

    @GetMapping("/{po_code}")
    public ResponseEntity<ProductOffering> getProductOfferingById(@PathVariable("po_code") int po_code) {
        ProductOffering productOffering = productOfferingService.findById(po_code);
        return ResponseEntity.ok(productOffering);
    }

    @PutMapping("/{po_code}")
    public ResponseEntity<?> updateProductOffering(
            @PathVariable("po_code") int po_code,
            @RequestBody ProductOffering updatedProductOffering) {

        try {
            ProductOffering updatedProductOfferingResult = productOfferingService.update(po_code, updatedProductOffering);
            return ResponseEntity.ok(updatedProductOfferingResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{po_code}")
    public ResponseEntity<String> deleteProductOffering(@PathVariable("po_code") int po_code) {
        String resultMessage = productOfferingService.delete(po_code);
        return ResponseEntity.ok(resultMessage);
    }


    @GetMapping("/search")
    public ResponseEntity<List<ProductOffering>> searchProductOfferingsByKeyword(@RequestParam("name") String name) {
        List<ProductOffering> searchResults = productOfferingService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
