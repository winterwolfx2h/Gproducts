package com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicalPortRepository extends JpaRepository<PhysicalPort, Integer> {
    Optional<PhysicalPort> findById(int PPID);

}
