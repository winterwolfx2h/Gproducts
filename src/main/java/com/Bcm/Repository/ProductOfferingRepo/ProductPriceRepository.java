package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Integer> {

  Optional<ProductPrice> findById(int productPriceCode);

  @Query("SELECT p FROM ProductPrice p WHERE p.cashPrice = :cashPrice")
  List<ProductPrice> searchByPrice(@Param("cashPrice") float cashPrice);

  boolean existsById(int productPriceCode);

  @Query(
      "SELECT pp FROM ProductPrice pp JOIN Product p ON pp.Product_id = p.Product_id WHERE p.Product_id = :productId")
  List<ProductPrice> findByProductId(@Param("productId") int productId);
}
