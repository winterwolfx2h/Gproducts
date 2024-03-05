package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOfferRelationRepository extends JpaRepository<ProductOfferRelation, Integer> {
    Optional<ProductOfferRelation> findById(int PoOfferRelation_Code);

    Optional<ProductOfferRelation> findByName(String name);

    @Query("SELECT p FROM ProductOfferRelation p WHERE p.name LIKE %:name% ")
    List<ProductOfferRelation> searchByKeyword(String name);

    /*@Query("SELECT po FROM ProductOfferRelation po JOIN po.type c WHERE  c.name = :name ")
    List<ProductOfferRelation> findAllWithType(String name);

    @Query("SELECT p FROM ProductOfferRelation p WHERE p.type.poRelationType_code = :poRelationType_code")
    List<ProductOfferRelation> findByType_poRelationType_code(int poRelationType_code);

    @Query("SELECT po FROM ProductOfferRelation po JOIN po.status c WHERE  c.name = :name ")
    List<ProductOfferRelation> findAllWithStatus(String name);

    @Query("SELECT p FROM ProductOfferRelation p WHERE p.status.pos_code = :pos_code")
    List<ProductOfferRelation> findByStatus_pos_code(int pos_code);*/

}

