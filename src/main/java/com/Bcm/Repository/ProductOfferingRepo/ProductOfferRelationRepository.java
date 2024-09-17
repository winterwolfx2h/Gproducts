package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.RelationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductOfferRelationRepository extends JpaRepository<ProductOfferRelation, Integer> {

    Optional<ProductOfferRelation> findByType(String type);

    @Query("SELECT p FROM ProductOfferRelation p WHERE p.type LIKE %:type% ")
    List<ProductOfferRelation> searchByKeyword(@Param("type") String type);

    Optional<ProductOfferRelation> findById(Integer productOfferRelCode);

    void deleteById(Integer productOfferRelCode);

    @Query(
            "SELECT new com.Bcm.Model.ProductOfferingABE.RelationResponse(p.productId, po.name) "
                    + "FROM ProductOfferRelation p "
                    + "JOIN Product po ON p.relatedProductId = po.Product_id "
                    + "WHERE p.productId = :productId AND p.type = 'Plan'")
    List<RelationResponse> findRelationsByProductId(@Param("productId") Integer productId);
}
