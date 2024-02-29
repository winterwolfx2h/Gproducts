package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;


import com.Bcm.Model.ProductOfferingABE.SubClasses.SubFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubFamilyRepository  extends JpaRepository<SubFamily, Integer> {
    Optional<SubFamily> findByName(String name);

    /*List<SubFamily> findByParentFamily(Family parentFamily);*/

    @Query("SELECT p FROM SubFamily p WHERE p.name = :name")
    List<SubFamily> searchByKeyword(String name);
}
