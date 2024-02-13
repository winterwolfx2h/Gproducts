package com.Bcm.Repository.ResourceSpecRepo.ResourceConfigRepo;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.ResourceConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceConfigurationRepository extends JpaRepository<ResourceConfiguration, Integer> {
    Optional<ResourceConfiguration> findById(int LRID);

}