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
    public ResponseEntity<Category> createCategory(@RequestBody Category Category) {
        Category createdCategory = categoryService.create(Category);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategorys() {
        List<Category> Categorys = categoryService.read();
        return ResponseEntity.ok(Categorys);
    }

    @GetMapping("/{po_CategoryCode}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("po_CategoryCode") int po_CategoryCode) {
        Category Category = categoryService.findById(po_CategoryCode);
        return ResponseEntity.ok(Category);
    }

    @PutMapping("/{po_CategoryCode}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable("po_CategoryCode") int po_CategoryCode,
            @RequestBody Category updatedCategory) {

        Category updatedGroup = categoryService.update(po_CategoryCode, updatedCategory);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_CategoryCode}")
    public ResponseEntity<String> deleteCategory(@PathVariable("po_CategoryCode") int po_CategoryCode) {
        String resultMessage = categoryService.delete(po_CategoryCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategorysByKeyword(@RequestParam("name") String name) {
        List<Category> searchResults = categoryService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
