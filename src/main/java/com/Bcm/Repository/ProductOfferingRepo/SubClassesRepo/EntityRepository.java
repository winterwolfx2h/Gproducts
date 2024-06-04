package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EntityRepository extends JpaRepository<Entity, Integer> {

    Optional<Entity> findById(int entityCode);

    Optional<Entity> findByName(String name);

    @Query("SELECT p FROM Entity p WHERE p.name = :name")
    List<Entity> searchByKeyword(String name);

    boolean existsByName(String name);
}
