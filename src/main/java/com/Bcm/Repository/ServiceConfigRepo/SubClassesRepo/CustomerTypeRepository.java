package com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo;

import com.Bcm.Model.ServiceABE.SubClasses.BusinessInteraction;
import com.Bcm.Model.ServiceABE.SubClasses.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Integer> {

    Optional<CustomerType> findById(int CT_code);

    @Query("SELECT p FROM CustomerType p WHERE p.name LIKE %:name% ")
    List<CustomerType> searchByKeyword(String name);
}
