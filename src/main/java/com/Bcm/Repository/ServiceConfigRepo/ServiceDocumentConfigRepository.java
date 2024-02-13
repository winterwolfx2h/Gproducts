package com.Bcm.Repository.ServiceConfigRepo;

import com.Bcm.Model.ServiceABE.ServiceDocumentConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ServiceDocumentConfigRepository extends JpaRepository<ServiceDocumentConfig, Integer> {

    Optional<ServiceDocumentConfig> findById(int SDC_code);
}

