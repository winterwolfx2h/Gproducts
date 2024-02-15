package com.Bcm.Repository.ResourceSpecRepo.LogicalResourceRepo;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.LogicalResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogicalResourceRepository extends JpaRepository<LogicalResource, Integer> {
    Optional<LogicalResource> findById(int LRID);

}
