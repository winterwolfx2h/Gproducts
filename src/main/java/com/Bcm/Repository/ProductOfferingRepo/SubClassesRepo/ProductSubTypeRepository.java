package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSubTypeRepository extends JpaRepository<ProductSubType, Integer> {

    Optional<ProductSubType> findById(int po_ProdSubTypeCode);

    @Query("SELECT p FROM ProductSubType p WHERE p.name LIKE %:name% ")
    List<ProductSubType> searchByKeyword(String name);
}
