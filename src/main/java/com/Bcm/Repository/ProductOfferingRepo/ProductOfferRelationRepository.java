package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.RelationResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductOfferRelationRepository extends JpaRepository<ProductOfferRelation, Integer> {

  @Query("SELECT p FROM ProductOfferRelation p WHERE p.type LIKE %:type% ")
  List<ProductOfferRelation> searchByKeyword(@Param("type") String type);

  Optional<ProductOfferRelation> findById(Integer productOfferRelCode);

  void deleteById(Integer productOfferRelCode);

  @Query(
      """
    SELECT new com.Bcm.Model.ProductOfferingABE.RelationResponse(por.productId, po.name)
    FROM ProductOfferRelation por
    JOIN Product po ON po.Product_id = por.relatedProductId
    WHERE por.relatedProductId IN (:relatedProductIds)
    ORDER BY por.productId
""")
  List<RelationResponse> findRelationsByRelatedProductIds(@Param("relatedProductIds") List<Integer> relatedProductIds);

  @Query(
      """
        SELECT DISTINCT new com.Bcm.Model.ProductOfferingABE.RelationResponse(p.Product_id, p.name)
        FROM Product p
        JOIN ProductOffering poff ON poff.Product_id = p.Product_id
        WHERE poff.poType <> 'PO-Plan'
        AND p.Product_id NOT IN (
            SELECT DISTINCT por.relatedProductId
            FROM ProductOfferRelation por
            WHERE por.productId = :selectedProductId
        )
        AND p.Product_id IN (
            SELECT DISTINCT por.productId
            FROM ProductOfferRelation por
            WHERE por.relatedProductId = (
                SELECT DISTINCT por2.relatedProductId
                FROM ProductOfferRelation por2
                WHERE por2.productId = :selectedProductId
                AND por2.type = 'Plan'
            )
            AND por.type = 'Plan'
        )
        ORDER BY p.Product_id
      """)
  List<RelationResponse> findAllProductsExceptRelated(@Param("selectedProductId") Integer selectedProductId);

  @Query("SELECT COUNT(p) FROM Product p WHERE p.Product_id = :productId")
  Integer countByProductId(@Param("productId") Integer productId);

  @Query("SELECT COUNT(por) FROM ProductOfferRelation por WHERE por.productId = :productId AND por.type = 'Plan'")
  Integer countPlanAssociations(@Param("productId") Integer productId);

  @Query(
      """
    SELECT DISTINCT new com.Bcm.Model.ProductOfferingABE.RelationResponse(p.productId, po.name, po.familyName, po.subFamily, p.relatedProductId, p.type, p.subType, pofo.markets, pofo.submarkets)
    FROM ProductOfferRelation p
    JOIN Product po ON p.relatedProductId = po.Product_id
    JOIN ProductOffering pofo ON po.Product_id = pofo.Product_id
    WHERE p.productId = :productId AND p.type = 'Plan'
""")
  List<RelationResponse> findPOPlanAssociations(@Param("productId") Integer productId);

  @Query(
      "SELECT COUNT(por) FROM ProductOfferRelation por WHERE por.productId = :productId AND (por.type = 'AutoInclude'"
          + " OR por.type = 'Optional')")
  Integer countMandatoryOptAssociations(@Param("productId") Integer productId);

  @Query(
      """
    SELECT DISTINCT new com.Bcm.Model.ProductOfferingABE.RelationResponse(p.productId, po.name, p.relatedProductId, p.type)
    FROM ProductOfferRelation p
    JOIN Product po ON p.relatedProductId = po.Product_id
    WHERE p.productId = :productId AND (p.type = 'AutoInclude' OR p.type = 'Optional')
""")
  List<RelationResponse> findMandatoryOptionsByProductId(@Param("productId") Integer productId);

  @Query(
      """
    SELECT DISTINCT new com.Bcm.Model.ProductOfferingABE.RelationResponse(
        por.productId, po.name, por.relatedProductId, por.type)
    FROM ProductOfferRelation por
    JOIN Product po ON por.relatedProductId = po.Product_id
    WHERE por.productId = :productId AND por.type NOT LIKE 'Plan'
""")
  List<RelationResponse> findProductAssociationsByProductId(@Param("productId") Integer productId);
}
