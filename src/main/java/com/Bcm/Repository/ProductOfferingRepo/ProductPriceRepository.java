package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Integer> {

    Optional<ProductPrice> findById(int productPriceCode);

    Optional<ProductPrice> findByprice(float price);

    @Query("SELECT p FROM ProductPrice p WHERE p.price = :price")
    List<ProductPrice> searchByPrice(float price);

}
