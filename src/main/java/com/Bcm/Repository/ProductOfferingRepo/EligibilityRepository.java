package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.Eligibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface EligibilityRepository extends JpaRepository<Eligibility, Integer> {

    Optional<Eligibility> findById(int eligibilityId);
    Optional<Eligibility> findByChannel(String channel);

    @Query("SELECT p FROM Eligibility p WHERE p.channel LIKE :channel ")
    List<Eligibility> searchByKeyword(String channel);
}
