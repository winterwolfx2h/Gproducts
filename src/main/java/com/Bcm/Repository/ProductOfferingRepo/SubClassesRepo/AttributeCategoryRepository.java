package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.AttributeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AttributeCategoryRepository extends JpaRepository<AttributeCategory, Integer> {

    Optional<AttributeCategory> findById(int po_AttributeCategoryCode);


    Optional<AttributeCategory> findByname(String name);

    @Query("SELECT p FROM AttributeCategory p WHERE p.name = :name")
    List<AttributeCategory> searchByKeyword(String name);

}
