package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.RelationType;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface RelationTypeRepository extends JpaRepository<RelationType, Integer> {
    Optional<RelationType> findById(int po_ParentCode);

    Optional<RelationType> findByName(String name);

    @Query("SELECT p FROM RelationType p WHERE p.name = :name")
    List<RelationType> searchByKeyword(String name);
}
