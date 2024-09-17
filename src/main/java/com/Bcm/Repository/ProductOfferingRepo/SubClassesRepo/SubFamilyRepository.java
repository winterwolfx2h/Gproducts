package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family.SubFamily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubFamilyRepository extends JpaRepository<SubFamily, Integer> {
    Optional<SubFamily> findBySubFamilyName(String subFamilyName);
}
