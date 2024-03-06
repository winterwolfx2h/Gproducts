package com.Bcm.Repository.ProductResourceRepository;

import com.Bcm.Model.ProductResourceABE.LogicalResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LogicalResourceRepository extends JpaRepository<LogicalResource, Integer> {
    Optional<LogicalResource> findById(int logResourceId);

    Optional<LogicalResource> findByLogicalResourceType(String logicalResourceType);

    @Query("SELECT p FROM LogicalResource p WHERE p.logicalResourceType = :logicalResourceType")
    List<LogicalResource> searchByKeyword(String logicalResourceType);

}

