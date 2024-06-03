package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.PrimeryKeyProductRelation;
import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOfferRelationRepository extends JpaRepository<ProductOfferRelation, Integer> {

    Optional<ProductOfferRelation> findByType(String type);

    @Query("SELECT p FROM ProductOfferRelation p WHERE p.type LIKE %:type% ")
    List<ProductOfferRelation> searchByKeyword(String type);

    Optional<ProductOfferRelation> findById(PrimeryKeyProductRelation id);

    void deleteById(PrimeryKeyProductRelation id);
}

