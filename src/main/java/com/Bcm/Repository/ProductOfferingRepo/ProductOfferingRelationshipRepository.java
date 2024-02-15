package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOfferingRelationshipRepository extends JpaRepository<ProductOfferingRelationship, Integer> {
    Optional<ProductOfferingRelationship> findById(int por_Code);

}

