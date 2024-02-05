package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.DateFinEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DateFinEngagementRepository extends JpaRepository<DateFinEngagement, Integer> {

    Optional<DateFinEngagement> findById(int po_DateFinEngCode);

    @Query("SELECT p FROM DateFinEngagement p WHERE p.name LIKE %:name% ")
    List<DateFinEngagement> searchByKeyword(String name);
}
