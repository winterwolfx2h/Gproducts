package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TypeRepository extends JpaRepository<Type, Integer> {
    Optional<Type> findById(int po_ParentCode);

    Optional<Type> findByName(String name);

    @Query("SELECT p FROM Type p WHERE p.name = :name")
    List<Type> searchByKeyword(String name);
}
