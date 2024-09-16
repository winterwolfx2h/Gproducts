package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductRelation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRelationRepository extends JpaRepository<ProductRelation, Integer> {
  Optional<ProductRelation> findById(int poRelation_Code);

  Optional<ProductRelation> findByType(String type);

  @Query("SELECT p FROM ProductRelation p WHERE p.type = :type")
  List<ProductRelation> searchByKeyword(String type);
}
