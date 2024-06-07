package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductPriceVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductPriceVersionRepository extends JpaRepository<ProductPriceVersion, Integer> {

    Optional<ProductPriceVersion> findById(int po_ProductPriceVersionCode);

    Optional<ProductPriceVersion> findByName(String name);

    @Query("SELECT p FROM ProductPriceVersion p WHERE p.name = :name")
    List<ProductPriceVersion> searchByKeyword(String name);

    boolean existsByName(String name);
}
