package com.GestionProduit.Repository;

import com.GestionProduit.Model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
  Optional<Produit> findById(long id);

  Optional<Produit> findByNom(String nom);

  @Query("SELECT p FROM Produit p WHERE LOWER(p.nom) LIKE %:keyword%")
  List<Produit> findByNomContaining(@Param("keyword") String keyword);
}
