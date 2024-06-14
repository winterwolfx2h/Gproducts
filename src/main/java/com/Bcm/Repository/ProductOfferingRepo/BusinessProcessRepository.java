package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessProcessRepository extends JpaRepository<BusinessProcess, Integer> {

  //  Optional<BusinessProcess> findById(int businessProcessId);
  //
  //  Optional<BusinessProcess> findByBussinessProcName(String name);
  //
  //      @Query("SELECT p FROM BusinessProcess p WHERE p.bussinessProcType LIKE :bussinessProcType ")
  //      List<BusinessProcess> searchByKeyword(String bussinessProcType);
}
