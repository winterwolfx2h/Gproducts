package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductPriceGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductPriceGroupRepository extends JpaRepository<ProductPriceGroup, Integer> {

  Optional<ProductPriceGroup> findById(int productPriceGroupCode);

  Optional<ProductPriceGroup> findByName(String name);

  @Query("SELECT p FROM ProductPriceGroup p WHERE p.name = :name")
  List<ProductPriceGroup> searchByKeyword(String name);

  boolean existsByName(String name);
}
