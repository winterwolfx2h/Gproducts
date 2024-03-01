package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.LrServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LrServiceIdRepository extends JpaRepository<LrServiceId, Integer> {

    Optional<LrServiceId> findById(int pr_LrServiceId);

    Optional<LrServiceId> findByName(String name);

    @Query("SELECT p FROM LrServiceId p WHERE p.name = :name")
    List<LrServiceId> searchByKeyword(String name);


}
