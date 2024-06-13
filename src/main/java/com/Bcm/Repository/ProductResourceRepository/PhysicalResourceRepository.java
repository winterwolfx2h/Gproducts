package com.Bcm.Repository.ProductResourceRepository;

import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhysicalResourceRepository extends JpaRepository<PhysicalResource, Integer> {
  Optional<PhysicalResource> findById(int PR_id);

  Optional<PhysicalResource> findByPhysicalResourceType(String physicalResourceType);

  List<PhysicalResource> findByName(String name);

  @Query("SELECT p FROM PhysicalResource p WHERE p.physicalResourceType = :physicalResourceType")
  List<PhysicalResource> searchByKeyword(String physicalResourceType);
}
