package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.PrServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrServiceIdRepository extends JpaRepository<PrServiceId, Integer> {

    Optional<PrServiceId> findById(int pr_PrServiceId);

    Optional<PrServiceId> findByName(String name);

    @Query("SELECT p FROM PrServiceId p WHERE p.name = :name")
    List<PrServiceId> searchByKeyword(String name);


}