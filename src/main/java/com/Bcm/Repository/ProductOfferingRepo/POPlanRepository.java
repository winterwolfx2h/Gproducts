package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.POPlan;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface POPlanRepository extends JpaRepository<POPlan, Integer> {

    Optional<POPlan> findById(int PO_ID);
    @Query("SELECT p FROM POPlan p WHERE p.name LIKE %:name% ")
    List<POPlan> searchByKeyword(String name);
}
