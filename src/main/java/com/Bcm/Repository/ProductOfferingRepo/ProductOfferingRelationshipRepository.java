package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductOfferingRelationshipRepository extends JpaRepository<ProductOfferingRelationship, Integer> {
    Optional<ProductOfferingRelationship> findById(int por_Code);

}

