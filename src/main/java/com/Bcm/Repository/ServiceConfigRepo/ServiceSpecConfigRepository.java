package com.Bcm.Repository.ServiceConfigRepo;

import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ServiceABE.ServiceSpecConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ServiceSpecConfigRepository extends JpaRepository<ServiceSpecConfig, Integer> {


    Optional<ServiceSpecConfig> findByServiceSpecType(String serviceSpecType);

    Optional<ServiceSpecConfig> findById(int SSC_code);

    @Query("SELECT p FROM ServiceSpecConfig p WHERE p.description LIKE %:description% ")
    List<ServiceSpecConfig> searchByKeyword(String description);
}

