package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.DateFinEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface DateFinEngagementRepository extends JpaRepository<DateFinEngagement, Integer> {

    Optional<DateFinEngagement> findById(int po_DateFinEngCode);

    Optional<DateFinEngagement> findByName(String name);

    @Query("SELECT p FROM DateFinEngagement p WHERE p.name = :name")
    List<DateFinEngagement> searchByKeyword(String name);
}
