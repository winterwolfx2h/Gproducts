package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.Eligibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EligibilityRepository extends JpaRepository<Eligibility, Integer> {
    Optional<Eligibility> findById(int eligibilityId);
}
