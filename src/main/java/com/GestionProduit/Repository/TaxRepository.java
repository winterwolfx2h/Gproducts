package com.GestionProduit.Repository;

import com.GestionProduit.Model.Tax;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaxRepository extends JpaRepository<Tax, Integer> {

  Optional<Tax> findById(int tax_code);

  @Query("SELECT t FROM Tax t JOIN t.produits p WHERE p.id = :id")
  List<Tax> findTaxesByProductId(@Param("id") long id);

  @Query("SELECT p FROM Tax p WHERE p.name = :name")
  List<Tax> searchByName(@Param("name") String name);

  boolean existsById(int tax_code);

  Optional<Tax> findTaxByName(String name);
}
