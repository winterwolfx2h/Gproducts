package com.GestionProduit.Service;

import com.GestionProduit.Model.Category;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

  Category create(Category category);

  List<Category> read();

  Category update(long id, Category category);

  String delete(long id);

  Category findById(long id);

  boolean existsById(long id);

  Optional<Category> searchByName(String nom);

  Map<String, Object> findAllCategoriesWithProducts();
}
