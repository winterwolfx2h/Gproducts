package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.Eligibility;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EligibilityRepository extends JpaRepository<Eligibility, Integer> {
  Optional<Eligibility> findById(int eligibilityId);
}
