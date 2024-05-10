package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubMarketRepository extends JpaRepository<SubMarket, Integer> {

    Optional<SubMarket> findById(int po_SubMarketCode);

    Optional<SubMarket> findByName(String name);

    @Query("SELECT p FROM SubMarket p WHERE p.name = :name")
    List<SubMarket> searchByKeyword(String name);

    boolean existsByName(String name);

}
