package com.Bcm.Repository.ResourceSpecRepo.LogicalResourceRepo.SubClasses;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses.LogicalResourceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LogicalResourceStatusRepository extends JpaRepository<LogicalResourceStatus, Integer> {

    Optional<LogicalResourceStatus> findById(int LRSID);

    @Query("SELECT p FROM LogicalResourceStatus p WHERE p.name LIKE %:name% ")
    List<LogicalResourceStatus> searchByKeyword(String name);
}

