package com.Bcm.Repository.ServiceConfigRepo;

import com.Bcm.Model.ServiceABE.ResourceFacingServiceSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResourceFacingServiceSpecRepository extends JpaRepository<ResourceFacingServiceSpec, Integer> {

    Optional<ResourceFacingServiceSpec> findById(int Rfss_code);

    Optional<ResourceFacingServiceSpec> findByName(String name);

    @Query("SELECT r FROM ResourceFacingServiceSpec r WHERE r.name LIKE %:name%")
    List<ResourceFacingServiceSpec> searchByKeyword(@Param("name") String name);

    boolean existsByName(String name);


}
