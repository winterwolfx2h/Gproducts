package com.Bcm.Repository.Product;



import com.Bcm.Model.product.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT po FROM Product po JOIN po.family c WHERE  c.name = :name ")
    List<Product> findAllWithFamily(String name);

}