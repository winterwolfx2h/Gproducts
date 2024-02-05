package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.DureeEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DureeEngagementRepository extends JpaRepository<DureeEngagement, Integer> {

    Optional<DureeEngagement> findById(int po_DureeEngCode);

    @Query("SELECT p FROM DureeEngagement p WHERE p.name LIKE %:name% ")
    List<DureeEngagement> searchByKeyword(String name);
}
