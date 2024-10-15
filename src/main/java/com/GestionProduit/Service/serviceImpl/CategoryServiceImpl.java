package com.GestionProduit.Service.serviceImpl;

import com.GestionProduit.Model.Category;
import com.GestionProduit.Model.Produit;
import com.GestionProduit.Repository.CategoryRepository;
import com.GestionProduit.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

  private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
  private static final String TID = "Category with ID ";
  final CategoryRepository categoryRepository;

  @Override
  public Category create(Category category) {
    logger.info("Creating category: {}", category);

    if (categoryRepository.findByNom(category.getNom()).isPresent()) {
      logger.warn("Category with name {} already exists", category.getNom());
      throw new RuntimeException("Category with name " + category.getNom() + " already exists");
    }

    Category savedCategory = categoryRepository.save(category);
    logger.info("Category created successfully: {}", savedCategory);
    return savedCategory;
  }

  @Override
  public List<Category> read() {
    logger.info("Retrieving all categories");
    try {
      return categoryRepository.findAll();
    } catch (Exception e) {
      logger.error("Error occurred while retrieving Categories", e);
      throw new RuntimeException("Error occurred while retrieving Categories");
    }
  }

  @Override
  public Category update(long id, Category updatedCategory) {
    logger.info("Updating category with ID: {}", id);
    Optional<Category> existingCategoryOptional = categoryRepository.findById(id);
    if (existingCategoryOptional.isPresent()) {
      Category existingCategory = existingCategoryOptional.get();
      existingCategory.setNom(updatedCategory.getNom());
      Category updated = categoryRepository.save(existingCategory);
      logger.info("Category updated successfully: {}", updated);
      return updated;
    } else {
      String message = "%s not found.".formatted(TID + id);
      logger.warn(message);
      throw new RuntimeException(message);
    }
  }

  @Override
  public String delete(long id) {
    logger.info("Deleting category with ID: {}", id);
    try {
      categoryRepository.deleteById(id);
      String message = TID + id + " was successfully deleted";
      logger.info(message);
      return message;
    } catch (IllegalArgumentException e) {
      logger.error("Invalid argument for deleting Category", e);
      throw new RuntimeException("Invalid argument provided for deleting Category");
    }
  }

  @Override
  public Category findById(long id) {
    logger.info("Finding category with ID: {}", id);
    try {
      Optional<Category> optionalCategory = categoryRepository.findById(id);
      return optionalCategory.orElseThrow(
          () -> {
            logger.warn("{} not found", TID + id);
            return new RuntimeException(TID + id + " not found");
          });
    } catch (IllegalArgumentException e) {
      logger.error("Invalid argument for finding Category", e);
      throw new RuntimeException("Invalid argument provided for finding Category");
    }
  }

  @Override
  public boolean existsById(long id) {
    logger.info("Checking existence for category ID: {}", id);
    return categoryRepository.existsById(id);
  }

  @Override
  public Optional<Category> searchByName(String nom) {
    logger.info("Searching for category by name: {}", nom);
    try {
      return categoryRepository.findByNom(nom);
    } catch (IllegalArgumentException e) {
      logger.error("Invalid argument for searching Category by name", e);
      throw new RuntimeException("Invalid argument provided for searching Category by name");
    }
  }

  @Override
  public Map<String, Object> findAllCategoriesWithProducts() {
    logger.info("Finding all categories with products");
    List<Category> categories = categoryRepository.findAll();

    categories.sort(Comparator.comparing(Category::getId));

    List<Map<String, Object>> response = new ArrayList<>();

    for (Category category : categories) {
      Map<String, Object> linkedProductsMap = new HashMap<>();

      Map<String, Object> categoryMap = new HashMap<>();
      categoryMap.put("id", category.getId());
      categoryMap.put("nom", category.getNom());

      List<Map<String, Object>> productsList = new ArrayList<>();
      for (Produit produit : category.getProduits()) {
        Map<String, Object> produitMap = new LinkedHashMap<>();
        produitMap.put("id", produit.getId());
        produitMap.put("nom", produit.getNom());
        produitMap.put("prix", produit.getPrix());
        produitMap.put("quantite", produit.getQuantite());
        productsList.add(produitMap);
      }

      if (!productsList.isEmpty()) {
        linkedProductsMap.put("Category", categoryMap);
        linkedProductsMap.put("Linked Products To That Category", productsList);
        response.add(linkedProductsMap);
      }
    }

    Map<String, Object> finalResponse = new HashMap<>();
    finalResponse.put("Result Count", response.size());
    finalResponse.put("Linked Products", response);

    logger.info("Retrieved all categories with products: {}", finalResponse);
    return finalResponse;
  }
}
