package com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceSpecType;
import com.Bcm.Model.ServiceABE.SubClasses.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceStatusRepository extends JpaRepository<ServiceStatus, Integer> {

    Optional<ServiceStatus> findById(int SS_code);

    @Query("SELECT p FROM ServiceStatus p WHERE p.name LIKE %:name% ")
    List<ServiceStatus> searchByKeyword(String name);
}
