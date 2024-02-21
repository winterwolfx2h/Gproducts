package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductTechnicalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductTechnicalTypeRepository extends JpaRepository<ProductTechnicalType, Integer> {

    Optional<ProductTechnicalType> findById(int po_ProdTechTypeCode);

    Optional<ProductTechnicalType> findByName(String name);

    @Query("SELECT p FROM ProductTechnicalType p WHERE p.name = :name")
    List<ProductTechnicalType> searchByKeyword(String name);
}
