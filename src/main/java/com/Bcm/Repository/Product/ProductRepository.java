package com.Bcm.Repository.Product;

import com.Bcm.Model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(int Product_id);

    List<Product> findByFamilyName(String familyName);

    List<Product> findByNameContainingIgnoreCase(String name);

    Optional<Product> findByName(String name);
}
