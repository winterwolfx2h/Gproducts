package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Integer> {

    Optional<ProductSpecification> findById(int po_code);

    @Query("SELECT p FROM ProductSpecification p WHERE p.name LIKE %:name% ")
    List<ProductSpecification> searchByKeyword(String name);
}
