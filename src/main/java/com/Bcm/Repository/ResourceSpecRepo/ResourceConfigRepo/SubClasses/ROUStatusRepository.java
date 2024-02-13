package com.Bcm.Repository.ResourceSpecRepo.ResourceConfigRepo.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ROUStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ROUStatusRepository extends JpaRepository<ROUStatus, Integer> {

    Optional<ROUStatus> findById(int RSID);

    @Query("SELECT p FROM ROUStatus p WHERE p.name LIKE %:name% ")
    List<ROUStatus> searchByKeyword(String name);
}