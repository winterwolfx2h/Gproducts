package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductSubTypeRepository extends JpaRepository<ProductSubType, Integer> {

    Optional<ProductSubType> findById(int po_ProdSubTypeCode);

    Optional<ProductSubType> findByName(String name);

    @Query("SELECT p FROM ProductSubType p WHERE p.name = :name")
    List<ProductSubType> searchByKeyword(String name);
}
