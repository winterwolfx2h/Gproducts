package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Category;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Parent;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Type;
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
        String categoryName = productOffering.getCategory().getName();
        String productSpecName = productOffering.getProductSpecification().getName();
        String parentName = productOffering.getParent().getName();
        String familyName = productOffering.getFamily().getName();
        String typeName = productOffering.getPoType().getName();

        Category category = categoryService.findByname(categoryName);
        ProductSpecification productSpec = productSpecificationService.findByName(productSpecName);
        Parent parent = parentService.findByName(parentName);
        Family family = familyService.findByName(familyName);
        Type type = typeService.findByName(typeName);

        if (category != null && productSpec != null && parent != null && family != null && type != null) {
            productOffering.setCategory(category);
            productOffering.setProductSpecification(productSpec);
            productOffering.setParent(parent);
            productOffering.setFamily(family);
            productOffering.setPoType(type);

            ProductOffering createdProduct = productOfferingService.create(productOffering);
            return ResponseEntity.ok(createdProduct);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (category == null) errorMessage.append(" Category with name: ").append(categoryName);
            if (productSpec == null) errorMessage.append(" ProductSpecification with name: ").append(productSpecName);
            if (parent == null) errorMessage.append(" Parent with name: ").append(parentName);
            if (family == null) errorMessage.append(" Family with name: ").append(familyName);
            if (type == null) errorMessage.append(" Type with name: ").append(typeName);
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

        String categoryName = updatedProductOffering.getCategory().getName();
        Category category = categoryService.findByname(categoryName);

        if (category != null) {
            updatedProductOffering.setCategory(category);
            ProductOffering updatedProduct = productOfferingService.update(po_code, updatedProductOffering);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.badRequest().body("Category not found with name: " + categoryName);
        }
    }


    @DeleteMapping("/{po_code}")
    public ResponseEntity<String> deleteProductOffering(@PathVariable("po_code") int po_code) {
        String resultMessage = productOfferingService.delete(po_code);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/searchByCategory")
    public ResponseEntity<List<ProductOffering>> searchPfBycategory(@RequestParam("name") String name) {
        List<ProductOffering> searchResults = productOfferingService.searchWithCategory(name);
        return ResponseEntity.ok(searchResults);
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
