package com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicalResourceRepository extends JpaRepository<PhysicalResource, Integer> {
    Optional<PhysicalResource> findById(int PRID);

}
