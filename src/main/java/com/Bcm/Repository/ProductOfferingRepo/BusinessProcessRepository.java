package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BusinessProcessRepository extends JpaRepository<BusinessProcess, Integer> {

    Optional<BusinessProcess> findById(int businessProcess_id);

    Optional<BusinessProcess> findByName(String name);

    @Query("SELECT p FROM BusinessProcess p WHERE p.name = :name")
    List<BusinessProcess> searchByKeyword(String name);

    boolean existsByName(String businessProcess_name);

    @Query("SELECT b FROM BusinessProcess b ORDER BY b.businessProcess_id")
    List<BusinessProcess> findAllOrderedByBusinessProcess();
}
