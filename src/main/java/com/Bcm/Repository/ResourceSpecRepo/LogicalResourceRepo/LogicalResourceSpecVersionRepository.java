package com.Bcm.Repository.ResourceSpecRepo.LogicalResourceRepo;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.LogicalResourceSpecVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogicalResourceSpecVersionRepository extends JpaRepository<LogicalResourceSpecVersion, Integer> {
    Optional<LogicalResourceSpecVersion> findById(int LRSVID);

}
