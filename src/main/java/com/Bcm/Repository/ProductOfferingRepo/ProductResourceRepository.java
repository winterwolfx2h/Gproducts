package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ProductResourceRepository extends JpaRepository<ProductResource, Integer> {

    Optional<ProductResource> findById(int PrResId);

    Optional<ProductResource> findByname(String name);

    @Query("SELECT p FROM ProductResource p WHERE p.name LIKE :name ")
    List<ProductResource> searchByKeyword(String name);
}
