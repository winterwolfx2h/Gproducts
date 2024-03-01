package com.Bcm.Repository.Product;


import com.Bcm.Model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Integer> {

}