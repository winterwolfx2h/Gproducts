package com.Bcm.Repository.ServiceConfigRepo;

import com.Bcm.Model.ServiceABE.ServiceBusinessInteractionConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceBusinessInteractionConfigRepository extends JpaRepository<ServiceBusinessInteractionConfig, Integer> {

    Optional<ServiceBusinessInteractionConfig> findById(int SBIC_code);

}

