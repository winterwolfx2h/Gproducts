package com.Bcm.Repository.Product;

import com.Bcm.Model.Product.ProductType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

  Optional<ProductType> findById(int productTypeCode);

  Optional<ProductType> findByTypeName(String typeName);

  @Query("SELECT p FROM Type p WHERE p.typeName LIKE :typeName ")
  List<ProductType> searchByKeyword(String typeName);
}
