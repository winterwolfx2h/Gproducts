package com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PhysicalComponentRepository extends JpaRepository<PhysicalComponent, Integer> {
    Optional<PhysicalComponent> findById(int PCpID);

}
