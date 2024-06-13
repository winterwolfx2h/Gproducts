package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Integer> {

  Optional<ProductPrice> findById(int productPriceCode);

  @Query("SELECT p FROM ProductPrice p WHERE p.cashPrice = :cashPrice")
  List<ProductPrice> searchByPrice(float cashPrice);
}
