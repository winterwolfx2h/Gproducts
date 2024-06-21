package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.PrimeryKeyProductRelation;
import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.RelationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductOfferRelationRepository extends JpaRepository<ProductOfferRelation, PrimeryKeyProductRelation> {

    Optional<ProductOfferRelation> findByType(String type);

    @Query("SELECT p FROM ProductOfferRelation p WHERE p.type LIKE %:type% ")
    List<ProductOfferRelation> searchByKeyword(String type);

    Optional<ProductOfferRelation> findById(PrimeryKeyProductRelation id);

    void deleteById(PrimeryKeyProductRelation id);

    @Query(
            "SELECT new com.Bcm.Model.ProductOfferingABE.RelationResponse(p.id.productId, po.name) "
                    + "FROM ProductOfferRelation p "
                    + "JOIN Product po ON p.id.relatedProductId = po.Product_id "
                    + "WHERE p.id.productId = :productId AND p.type = 'Plan'")
    List<RelationResponse> findRelationsByProductId(@Param("productId") Integer productId);
}
