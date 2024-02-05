package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {
    Optional<Parent> findById(int po_ParentCode);

    @Query("SELECT p FROM Parent p WHERE p.name LIKE %:name% ")
    List<Parent> searchByKeyword(String name);
}
