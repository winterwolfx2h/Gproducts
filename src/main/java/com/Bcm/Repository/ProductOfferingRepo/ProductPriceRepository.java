package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Integer> {

    Optional<ProductPrice> findById(int productPriceCode);

    @Query("SELECT p FROM ProductPrice p WHERE p.cashPrice = :cashPrice")
    List<ProductPrice> searchByPrice(@Param("cashPrice") float cashPrice);

    boolean existsById(int productPriceCode);
}
