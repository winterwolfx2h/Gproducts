package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRelationRepository extends JpaRepository<ProductRelation, Integer> {
    Optional<ProductRelation> findById(int poRelation_Code);

    @Query("SELECT po FROM ProductRelation po JOIN po.type c WHERE  c.name = :name ")
    List<ProductRelation> findAllWithRelationType(String name);

    @Query("SELECT p FROM ProductRelation p WHERE p.type.poRelationType_code = :poRelationType_code")
    List<ProductRelation> findByRelationType_poRelationType_code(int poRelationType_code);


}

