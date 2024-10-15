package com.GestionProduit.Controller;

import com.GestionProduit.Model.Category;
import com.GestionProduit.Service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category Controller", description = "All of the Category methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/categories")
public class CategoryController {

  private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
  final CategoryService categoryService;

  @PostMapping("/addCategory")
  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
    logger.info("Request to create category: {}", category);
    try {
      Category savedCategory = categoryService.create(category);
      logger.info("Category created successfully: {}", savedCategory);
      return ResponseEntity.ok(savedCategory);
    } catch (RuntimeException e) {
      logger.error("Error creating category: {}", e.getMessage());
      return ResponseEntity.badRequest().body(null);
    }
  }

  @GetMapping("/listCategory")
  public ResponseEntity<List<Category>> listCategory() {
    logger.info("Request to list all categories");
    List<Category> categories = categoryService.read();
    logger.info("Retrieved categories: {}", categories);
    return ResponseEntity.ok(categories);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategory(@PathVariable Long id) {
    logger.info("Request to get category with ID: {}", id);
    try {
      Category category = categoryService.findById(id);
      logger.info("Category found: {}", category);
      return ResponseEntity.ok(category);
    } catch (RuntimeException e) {
      logger.error("Category with ID {} not found: {}", id, e.getMessage());
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/updateCategory/{id}")
  public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
    logger.info("Request to update category with ID: {}", id);
    try {
      Category savedCategory = categoryService.update(id, updatedCategory);
      logger.info("Category updated successfully: {}", savedCategory);
      return ResponseEntity.ok(savedCategory);
    } catch (RuntimeException e) {
      logger.error("Error updating category with ID {}: {}", id, e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/deleteCategory/{id}")
  public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
    logger.info("Request to delete category with ID: {}", id);
    try {
      String message = categoryService.delete(id);
      logger.info(message);
      return ResponseEntity.ok(message);
    } catch (RuntimeException e) {
      logger.error("Error deleting category with ID {}: {}", id, e.getMessage());
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/getAllCategoriesWithProducts")
  public ResponseEntity<List<Map<String, Object>>> getAllCategoriesWithProducts() {
    logger.info("Request to get all categories with products");
    Map<String, Object> response = categoryService.findAllCategoriesWithProducts();
    logger.info("Retrieved all categories with products: {}", response);
    return ResponseEntity.ok(Collections.singletonList(response));
  }
}
