package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.Market;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarketRepository extends JpaRepository<Market, Integer> {

  Optional<Market> findById(int po_MarketCode);

  Optional<Market> findByName(String name);

  @Query("SELECT p FROM Market p WHERE p.name = :name")
  List<Market> searchByKeyword(String name);

  boolean existsByName(String name);
}
