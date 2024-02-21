package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ParentRepository extends JpaRepository<Parent, Integer> {
    Optional<Parent> findById(int po_ParentCode);

    Optional<Parent> findByname(String name);

    Optional<Parent> findByName(String name);

    @Query("SELECT p FROM Parent p WHERE p.name = :name")
    List<Parent> searchByKeyword(String name);
}
