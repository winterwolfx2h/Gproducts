package com.Bcm.Repository.ServiceConfigRepo;

import com.Bcm.Model.ServiceABE.ResourceFacingServiceSpec;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceFacingServiceSpecRepository extends JpaRepository<ResourceFacingServiceSpec, Integer> {

  Optional<ResourceFacingServiceSpec> findById(int Rfss_code);

  Optional<ResourceFacingServiceSpec> findByexternalNPCode(String externalNPCode);

  @Query("SELECT r FROM ResourceFacingServiceSpec r WHERE r.externalNPCode LIKE %:externalNPCode%")
  List<ResourceFacingServiceSpec> searchByKeyword(@Param("externalNPCode") String externalNPCode);

  boolean existsByexternalNPCode(String externalNPCode);
}
