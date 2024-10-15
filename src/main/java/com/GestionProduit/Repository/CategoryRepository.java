package com.GestionProduit.Repository;

import com.GestionProduit.Model.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Optional<Category> findById(long id);

  Optional<Category> findByNom(String nom);
}
