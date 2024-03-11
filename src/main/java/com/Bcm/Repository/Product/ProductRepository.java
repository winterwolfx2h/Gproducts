package com.Bcm.Repository.Product;


import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByFamily(String familyName);

    List<Product> findByFamily_Name(String familyName);
    List<ProductOffering> findByParent(String parentName);

}