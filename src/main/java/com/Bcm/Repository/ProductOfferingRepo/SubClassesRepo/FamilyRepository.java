package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Integer> {

    Optional<Family> findById(int po_FamilyCode);

    Optional<Family> findByName(String name);

    @Query("SELECT p FROM Family p WHERE p.name = :name")
    List<Family> searchByKeyword(String name);

    boolean existsByName(String name);
}
