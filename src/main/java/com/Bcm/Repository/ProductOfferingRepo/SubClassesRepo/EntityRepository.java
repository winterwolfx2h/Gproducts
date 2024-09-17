package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EntityRepository extends JpaRepository<EligibilityEntity, Integer> {

    Optional<EligibilityEntity> findById(int entityCode);

    Optional<EligibilityEntity> findByName(String name);

    @Query("SELECT p FROM EligibilityEntity p WHERE p.name = :name")
    List<EligibilityEntity> searchByKeyword(String name);

    boolean existsByName(String name);

    boolean existsById(int entityCode);
}
