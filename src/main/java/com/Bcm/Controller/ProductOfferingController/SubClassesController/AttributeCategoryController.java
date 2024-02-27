package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.AttributeCategory;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.AttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/AttributeAttributeCategory")
public class AttributeCategoryController {

    @Autowired
    private AttributeCategoryService attributeCategoryService;

    @PostMapping
    public ResponseEntity<AttributeCategory> createAttributeCategory(@RequestBody AttributeCategory AttributeCategory) {
        AttributeCategory createdAttributeCategory = attributeCategoryService.create(AttributeCategory);
        return ResponseEntity.ok(createdAttributeCategory);
    }

    @GetMapping
    public ResponseEntity<List<AttributeCategory>> getAllCategories() {
        List<AttributeCategory> categories = attributeCategoryService.read();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{po_AttributeCategoryCode}")
    public ResponseEntity<AttributeCategory> getAttributeCategoryById(@PathVariable("po_AttributeCategoryCode") int po_AttributeCategoryCode) {
        AttributeCategory AttributeCategory = attributeCategoryService.findById(po_AttributeCategoryCode);
        return ResponseEntity.ok(AttributeCategory);
    }

    @PutMapping("/{po_AttributeCategoryCode}")
    public ResponseEntity<AttributeCategory> updateAttributeCategory(
            @PathVariable("po_AttributeCategoryCode") int po_AttributeCategoryCode,
            @RequestBody AttributeCategory updatedAttributeCategory) {

        AttributeCategory updated = attributeCategoryService.update(po_AttributeCategoryCode, updatedAttributeCategory);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{po_AttributeCategoryCode}")
    public ResponseEntity<String> deleteAttributeCategory(@PathVariable("po_AttributeCategoryCode") int po_AttributeCategoryCode) {
        String resultMessage = attributeCategoryService.delete(po_AttributeCategoryCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AttributeCategory>> searchCategoriesByKeyword(@RequestParam("name") String name) {
        List<AttributeCategory> searchResults = attributeCategoryService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
