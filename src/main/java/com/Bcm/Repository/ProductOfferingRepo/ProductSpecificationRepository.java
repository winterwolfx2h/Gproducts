package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Integer> {
    Optional<ProductSpecification> findById(int po_SpecCode);

    @Query("SELECT ps FROM ProductSpecification ps WHERE :poPlanSHDES MEMBER OF ps.poPlanSHDES")
    ProductSpecification findByPoPlanSHDES(@Param("poPlanSHDES") String poPlanSHDES);

    boolean existsByPoPlanSHDES(String poPlanSHDES);
}
