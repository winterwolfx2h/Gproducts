package com.Bcm.Repository.ProductResourceRepository;

import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PhysicalResourceRepository extends JpaRepository<PhysicalResource, Integer> {
    Optional<PhysicalResource> findById(int phyResourceId);

    Optional<PhysicalResource> findByPhysicalResourceType(String physicalResourceType);

    @Query("SELECT p FROM PhysicalResource p WHERE p.physicalResourceType = :physicalResourceType")
    List<PhysicalResource> searchByKeyword(String physicalResourceType);

}

