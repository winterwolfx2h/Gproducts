package com.Bcm.Repository.ResourceSpecRepo.ResourceConfigRepo;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.ResourceOrderAndUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceOrderAndUsageRepository extends JpaRepository<ResourceOrderAndUsage, Integer> {
    Optional<ResourceOrderAndUsage> findById(int ROUID);
}