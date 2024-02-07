package com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo;

import com.Bcm.Model.ServiceABE.SubClasses.BusinessInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessInteractionRepository extends JpaRepository<BusinessInteraction, Integer> {

    Optional<BusinessInteraction> findById(int BI_code);

    @Query("SELECT p FROM BusinessInteraction p WHERE p.name LIKE %:name% ")
    List<BusinessInteraction> searchByKeyword(String name);
}
