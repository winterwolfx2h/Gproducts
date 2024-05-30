package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingRepository extends JpaRepository<ProductOffering, Integer> {

    Optional<ProductOffering> findById(int po_code);

    Optional<ProductOffering> findByname(String name);

    List<ProductOffering> findByPoType(String poType);

    List<ProductOffering> findByFamilyName(String familyName);
/*
    List<ProductOffering> findByEligibility(String eligibility);

 */

    @Query("SELECT p FROM ProductOffering p WHERE p.name = :name")
    List<ProductOffering> searchByKeyword(String name);

    Optional<ProductOffering> findByName(String name);

}