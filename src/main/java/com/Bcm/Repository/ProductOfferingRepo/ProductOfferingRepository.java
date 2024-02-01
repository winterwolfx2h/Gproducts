package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOfferingRepository extends JpaRepository<ProductOffering,Integer>{
    Optional<ProductOffering> findById(int po_code);
    @Query("SELECT p FROM ProductOffering p WHERE p.name LIKE %:name% ")
    List<ProductOffering> searchByKeyword(String name);
}

