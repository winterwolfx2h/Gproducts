package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaxRepository extends JpaRepository<Tax, Integer> {

  Optional<Tax> findById(int tax_code);

  @Query("SELECT t FROM Tax t JOIN t.products p WHERE p.Product_id = :productId")
  List<Tax> findTaxesByProductId(@Param("productId") int productId);

  @Query("SELECT p FROM Tax p WHERE p.name = :name")
  List<Tax> searchByName(@Param("name") String name);

  boolean existsById(int tax_code);

  Optional<Tax> findTaxByName(String name);
}
