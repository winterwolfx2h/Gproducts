package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.BusinessProcess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BusinessProcessRepository extends JpaRepository<BusinessProcess, Integer> {

    Optional<BusinessProcess> findById(int businessProcessId);

    Optional<BusinessProcess> findByBussinessProcType(String bussinessProcType);

    @Query("SELECT p FROM BusinessProcess p WHERE p.bussinessProcType LIKE :bussinessProcType ")
    List<BusinessProcess> searchByKeyword(String bussinessProcType);
}
