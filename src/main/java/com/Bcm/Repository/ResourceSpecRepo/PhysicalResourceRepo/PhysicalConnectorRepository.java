package com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalConnector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicalConnectorRepository extends JpaRepository<PhysicalConnector, Integer> {
    Optional<PhysicalConnector> findById(int PCnID);

}