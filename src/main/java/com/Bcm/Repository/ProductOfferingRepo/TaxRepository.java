package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.Tax;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaxRepository extends JpaRepository<Tax, Integer> {

  Optional<Tax> findById(int taxCode);

  @Query("SELECT p FROM Tax p WHERE p.name = :name")
  List<Tax> searchByName(@Param("name") String name);

  boolean existsById(int taxCode);
}
