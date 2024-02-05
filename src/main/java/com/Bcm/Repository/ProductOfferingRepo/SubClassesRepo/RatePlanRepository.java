package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.RatePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatePlanRepository extends JpaRepository<RatePlan, Integer> {

    Optional<RatePlan> findById(int po_RatePlanCode);

    @Query("SELECT p FROM RatePlan p WHERE p.name LIKE %:name% ")
    List<RatePlan> searchByKeyword(String name);
}
