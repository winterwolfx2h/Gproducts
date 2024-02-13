package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Category;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.create(category);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.read();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{po_CategoryCode}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("po_CategoryCode") int po_CategoryCode) {
        Category category = categoryService.findById(po_CategoryCode);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{po_CategoryCode}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable("po_CategoryCode") int po_CategoryCode,
            @RequestBody Category updatedCategory) {

        Category updated = categoryService.update(po_CategoryCode, updatedCategory);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{po_CategoryCode}")
    public ResponseEntity<String> deleteCategory(@PathVariable("po_CategoryCode") int po_CategoryCode) {
        String resultMessage = categoryService.delete(po_CategoryCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategoriesByKeyword(@RequestParam("name") String name) {
        List<Category> searchResults = categoryService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
