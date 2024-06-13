package com.Bcm.Repository.ProductResourceRepository;

import com.Bcm.Model.ProductResourceABE.LogicalResource;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LogicalResourceRepository extends JpaRepository<LogicalResource, Integer> {
  Optional<LogicalResource> findById(int LR_id);

  Optional<LogicalResource> findByLogicalResourceType(String logicalResourceType);

  @Query("SELECT p FROM LogicalResource p WHERE p.logicalResourceType = :logicalResourceType")
  List<LogicalResource> searchByKeyword(String logicalResourceType);

  Optional<LogicalResource> findByName(String name);

  boolean existsByName(String name);
}
