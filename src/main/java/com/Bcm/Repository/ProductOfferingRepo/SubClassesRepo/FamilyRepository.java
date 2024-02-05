package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Integer> {

    Optional<Family> findById(int po_FamilyCode);

    @Query("SELECT p FROM Family p WHERE p.name LIKE %:name% ")
    List<Family> searchByKeyword(String name);
}
