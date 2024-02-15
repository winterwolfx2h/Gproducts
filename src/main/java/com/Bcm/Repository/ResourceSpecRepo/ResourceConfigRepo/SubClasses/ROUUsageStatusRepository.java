package com.Bcm.Repository.ResourceSpecRepo.ResourceConfigRepo.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ROUUsageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ROUUsageStatusRepository extends JpaRepository<ROUUsageStatus, Integer> {

    Optional<ROUUsageStatus> findById(int RSID);

    @Query("SELECT p FROM ROUUsageStatus p WHERE p.name LIKE %:name% ")
    List<ROUUsageStatus> searchByKeyword(String name);
}