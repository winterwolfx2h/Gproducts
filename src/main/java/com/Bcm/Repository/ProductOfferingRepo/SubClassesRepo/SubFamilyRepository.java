package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family.SubFamily;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubFamilyRepository extends JpaRepository<SubFamily, Integer> {
  Optional<SubFamily> findBySubFamilyName(String subFamilyName);
}
