package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingRelationRepository extends JpaRepository<ProductOfferingRelation, Integer> {
    Optional<ProductOfferingRelation> findById(int poRelation_Code);

    Optional<ProductOfferingRelation> findByName(String name);

    @Query("SELECT p FROM ProductOfferingRelation p WHERE p.name LIKE %:name% ")
    List<ProductOfferingRelation> searchByKeyword(String name);

    @Query("SELECT po FROM ProductOfferingRelation po JOIN po.type c WHERE  c.name = :name ")
    List<ProductOfferingRelation> findAllWithType(String name);

    @Query("SELECT p FROM ProductOfferingRelation p WHERE p.type.poType_code = :poType_code")
    List<ProductOfferingRelation> findByType_poType_code(int poType_code);


}

