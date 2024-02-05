package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market, Integer> {

    Optional<Market> findById(int po_MarketCode);

    @Query("SELECT p FROM Market p WHERE p.name LIKE %:name% ")
    List<Market> searchByKeyword(String name);
}
