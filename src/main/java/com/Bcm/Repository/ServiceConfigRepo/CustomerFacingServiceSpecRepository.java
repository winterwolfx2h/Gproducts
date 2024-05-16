package com.Bcm.Repository.ServiceConfigRepo;

import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerFacingServiceSpecRepository extends JpaRepository<CustomerFacingServiceSpec, Integer> {

    Optional<CustomerFacingServiceSpec> findByServiceSpecType(String serviceSpecType);

    Optional<CustomerFacingServiceSpec> findById(int serviceId);

    @Query("SELECT p FROM CustomerFacingServiceSpec p WHERE p.description LIKE %:description% ")
    List<CustomerFacingServiceSpec> searchByKeyword(String description);
}

